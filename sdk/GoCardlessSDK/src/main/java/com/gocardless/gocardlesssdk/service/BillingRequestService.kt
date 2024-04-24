package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.BankAuthorisation
import com.gocardless.gocardlesssdk.model.BankAuthorisationWrapper
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestActionType
import com.gocardless.gocardlesssdk.model.BillingRequestList
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import com.gocardless.gocardlesssdk.model.CollectBankAccount
import com.gocardless.gocardlesssdk.model.CollectCustomerDetailsRequest
import com.gocardless.gocardlesssdk.model.ConfirmPayerDetailsRequest
import com.gocardless.gocardlesssdk.model.GenericRequest
import com.gocardless.gocardlesssdk.model.MetaData
import com.gocardless.gocardlesssdk.model.NotifyActionRequest
import com.gocardless.gocardlesssdk.model.SelectInstitution
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiResult
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi

/**
 * A Billing Request enables you to collect all types of GoCardless payments using
 * the Billing Request Flow API. This includes both one-off and recurring payments
 * from your new or existing customers.
 */
class BillingRequestService(
    private val goCardlessAPI: GoCardlessApi,
    private val errorMapper: ErrorMapper,
) {
    /**
     * Creates a Billing Request, enabling you to collect all types of GoCardless payments using
     * the Billing Request Flow API. This includes both one-off and recurring payments
     * from your new or existing customers.
     *
     * @param billingRequest The Billing Request to create.
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun createBillingRequest(billingRequest: BillingRequest): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequests(BillingRequestWrapper(billingRequest))

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: billingRequest)
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * If the billing request has a pending collect_customer_details action,
     * this endpoint can be used to collect the details in order to complete it.
     *
     * The endpoint takes the same payload as Customers, but checks that the customer
     * fields are populated correctly for the billing request scheme.
     *
     * Whatever is provided to this endpoint is used to update the referenced customer,
     * and will take effect immediately after the request is successful.
     *
     * @param billingRequestId The Billing Request to collect customer details.
     * @param collectCustomerDetails Customer and their billing details
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun collectCustomerDetails(
        billingRequestId: String,
        collectCustomerDetails: CollectCustomerDetailsRequest
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(collectCustomerDetails),
            billingRequestId,
            BillingRequestActionType.CollectCustomerDetails.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * If the billing request has a pending collect_bank_account action,
     * this endpoint can be used to collect the details in order to complete it.
     *
     * The endpoint takes the same payload as Customer Bank Accounts,
     * but check the bank account is valid for the billing request scheme before creating and attaching it.
     *
     * If the scheme is PayTo and the pay_id is available,
     * this can be included in the payload along with the country_code.
     *
     * @param billingRequestId The Billing Request to collect bank account.
     * @param collectBankAccount Customer bank account details
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun collectBankAccount(
        billingRequestId: String,
        collectBankAccount: CollectBankAccount,
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(collectBankAccount),
            billingRequestId,
            BillingRequestActionType.CollectBankAccount.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Creates an Institution object and attaches it to the Billing Request
     *
     * @param billingRequestId The Billing Request to select institution.
     * @param selectInstitution The institution details
     */
    suspend fun selectInstitution(
        billingRequestId: String,
        selectInstitution: SelectInstitution,
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(selectInstitution),
            billingRequestId,
            BillingRequestActionType.SelectInstitution.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created against a specific bank, usually the bank that provides the payer’s account.
     *
     * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing Request Flows) to ensure we meet regulatory requirements for checkout flows.
     *
     * @param billingRequestId The Billing Request to authorise.
     * @param bankAuthorisation the authorisation details
     */
    suspend fun createBankAuthorisation(
        bankAuthorisation: BankAuthorisation,
    ): ApiResult<BankAuthorisation> {
        val response = goCardlessAPI.bankAuthorisation(
            BankAuthorisationWrapper(bankAuthorisation)
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.bankAuthorisations ?: BankAuthorisation())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * This is needed when you have a mandate request. As a scheme compliance rule we are
     * required to allow the payer to crosscheck the details entered by them and confirm it.
     *
     * @param billingRequestId The Billing Request to confirm payer details.
     * @param confirmPayerDetailsRequest Payer details
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun confirmPayerDetails(
        billingRequestId: String,
        confirmPayerDetailsRequest: ConfirmPayerDetailsRequest? = null
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(confirmPayerDetailsRequest),
            billingRequestId,
            BillingRequestActionType.ConfirmPayerDetails.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * If a billing request is ready to be fulfilled, call this endpoint to cause it to fulfil,
     * executing the payment.
     *
     * @param billingRequestId The Billing Request to be fulfilled.
     * @param metaData Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and values up to 500 characters.
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun fulfil(
        billingRequestId: String,
        metaData: MetaData? = null,
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(metaData),
            billingRequestId,
            BillingRequestActionType.Fulfil.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Immediately cancels a billing request, causing all billing request flows to expire.
     *
     * @param billingRequestId The Billing Request to be cancelled.
     * @param metaData Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and values up to 500 characters.
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun cancel(
        billingRequestId: String,
        metaData: MetaData? = null,
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(metaData),
            billingRequestId,
            BillingRequestActionType.Cancel.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Notifies the customer linked to the billing request, asking them to authorise it.
     * Currently, the customer can only be notified by email.
     *
     * This endpoint is currently supported only for Instant Bank Pay Billing Requests.
     *
     * @param billingRequestId The Billing Request to be notified.
     * @param notifyActionRequest Notification body.
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun notify(
        billingRequestId: String,
        notifyActionRequest: NotifyActionRequest
    ): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequestsActions(
            GenericRequest(notifyActionRequest),
            billingRequestId,
            BillingRequestActionType.Notify.value
        )

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Fetches a billing request
     *
     * @param billingRequestId The Billing Request to collect customer details.
     * @return An [ApiResult] containing the Billing Request or an error.
     */
    suspend fun getBillingRequest(billingRequestId: String): ApiResult<BillingRequest> {
        val response = goCardlessAPI.getBillingRequest(billingRequestId)

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: BillingRequest())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Returns a cursor-paginated list of your billing requests.
     *
     * @return An [ApiResult] containing the list of Billing Request or an error.
     */
    suspend fun listBillingRequests(): ApiResult<BillingRequestList> {
        val response = goCardlessAPI.getBillingRequests()

        return if (response.isSuccessful) {
            ApiSuccess(response.body() ?: BillingRequestList())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }
}