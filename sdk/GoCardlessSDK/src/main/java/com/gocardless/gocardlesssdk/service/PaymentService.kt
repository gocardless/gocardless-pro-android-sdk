package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.Payment
import com.gocardless.gocardlesssdk.model.PaymentWrapper
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiResult
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi

/**
 * Payment objects represent payments from a customer to a creditor, taken against a Direct Debit mandate.
 */
class PaymentService(
    private val goCardlessAPI: GoCardlessApi,
    private val errorMapper: ErrorMapper,
) {
    /**
     * Creates a new payment object.
     *
     * This fails with a mandate_is_inactive error if the linked mandate is cancelled or has failed.
     * Payments can be created against mandates with status of: pending_customer_approval,
     * pending_submission, submitted, and active.
     *
     * @param payment The Payment Request to create.
     * @return An [ApiResult] containing the created Billing Request or an error.
     */
    suspend fun createPaymentRequest(payment: Payment): ApiResult<Payment> {
        val response = goCardlessAPI.createPayment(PaymentWrapper(payment))

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.payments ?: payment)
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }

    /**
     * Retrieves the details of a single existing payment.
     *
     * @param paymentId The Payment Id.
     * @return An [ApiResult] containing the Payment Request or an error.
     */
    suspend fun getPayment(paymentId: String): ApiResult<Payment> {
        val response = goCardlessAPI.getPayment(paymentId)

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.payments ?: Payment())
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }
}