package com.gocardless.app.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocardless.app.App.Companion.environment
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.model.BankAuthorisation
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.CollectCustomerDetailsRequest
import com.gocardless.gocardlesssdk.model.Customer
import com.gocardless.gocardlesssdk.model.Links
import com.gocardless.gocardlesssdk.model.MandateConstraints
import com.gocardless.gocardlesssdk.model.MandateRequest
import com.gocardless.gocardlesssdk.model.PaymentRequest
import com.gocardless.gocardlesssdk.model.Period
import com.gocardless.gocardlesssdk.model.PeriodicLimit
import com.gocardless.gocardlesssdk.model.SelectInstitution
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.Environment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainUiState {
    object Init : MainUiState()
    object Loading : MainUiState()
    data class Success(val url: String?) : MainUiState()
    data class Error(val message: String) : MainUiState()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Init)
    val uiState: StateFlow<MainUiState> = _uiState

    fun createSinglePayment() {
        createBillingRequestFlow(
            BillingRequest(
                paymentRequest = PaymentRequest(
                    currency = "GBP",
                    amount = 100,
                    description = "Description"
                )
            )
        )
    }

    fun createMandate() {
        createBillingRequestFlow(
            BillingRequest(
                mandateRequest = MandateRequest(
                    currency = "GBP",
                    description = "Description"
                )
            )
        )
    }

    fun createVRPMandate() {
        createBillingRequestFlow(
            BillingRequest(
                mandateRequest = MandateRequest(
                    currency = "GBP",
                    description = "Description",
                    scheme = "faster_payments",
                    sweeping = true,
                    constraints = MandateConstraints(
                        maxAmountPerPayment = 100,
                        periodicLimits = listOf(
                            PeriodicLimit(
                                period = Period.MONTH,
                                maxTotalAmount = 100,
                            )
                        )
                    )
                )
            )
        )
    }

    private fun createBillingRequestFlow(br: BillingRequest) {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {

            val brResponse = GoCardlessSDK.billingRequestService.createBillingRequest(br)
            when (brResponse) {
                is ApiSuccess -> {
                    val brfResponse =
                        GoCardlessSDK.billingRequestFlowService.createBillingRequestFlow(
                            BillingRequestFlow(
                                links = Links(billingRequest = brResponse.value.id)
                            )
                        )
                    when (brfResponse) {
                        is ApiSuccess -> _uiState.value = MainUiState.Success(brfResponse.value.authorisationUrl)
                        is ApiError -> returnError(brfResponse)
                    }
                }

                is ApiError -> returnError(brResponse)
            }
        }
    }

    private fun returnError(er: ApiError<*>) {
        _uiState.value =
            MainUiState.Error("Error creating Billing Request ${er.error?.errorDetail}")
    }

    fun createCustomPagePayment() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {

            val br = BillingRequest(
                paymentRequest = PaymentRequest(
                    currency = "GBP",
                    amount = 100,
                    description = "Description"
                )
            )
            val brResponse = GoCardlessSDK.billingRequestService.createBillingRequest(br)
            when (brResponse) {
                is ApiSuccess -> {
                    val billingRequestId = brResponse.value.id!!
                    val brCustomerDetailsResponse =
                        GoCardlessSDK.billingRequestService.collectCustomerDetails(
                            billingRequestId = billingRequestId,
                            collectCustomerDetails = CollectCustomerDetailsRequest(
                                customer = Customer(
                                    givenName = "User",
                                    familyName = "Test",
                                    email = "test@gocardless.com"
                                )

                            )
                        )

                    if (brCustomerDetailsResponse is ApiError)
                        returnError(brCustomerDetailsResponse)

                    val institutionId =
                        if (environment == Environment.Sandbox) "read_refund_account_sandbox_bank"
                        else "MONZO_MONZGB2L"

                    val brSelectInstitutionResponse =
                        GoCardlessSDK.billingRequestService.selectInstitution(
                            billingRequestId = billingRequestId,
                            selectInstitution = SelectInstitution(
                                countryCode = "GB",
                                institution = institutionId
                            )
                        )

                    if (brSelectInstitutionResponse is ApiError)
                        returnError(brSelectInstitutionResponse)

                    val brCreateAuthorisationResponse =
                        GoCardlessSDK.billingRequestService.createBankAuthorisation(
                            bankAuthorisation = BankAuthorisation(
                                links = Links(
                                    billingRequest = billingRequestId
                                )
                            )
                        )

                    when (brCreateAuthorisationResponse) {
                        is ApiSuccess -> _uiState.value = MainUiState.Success(
                            brCreateAuthorisationResponse.value.url!!)
                        is ApiError -> returnError(brCreateAuthorisationResponse)
                    }
                }

                is ApiError -> returnError(brResponse)
            }
        }
    }
}
