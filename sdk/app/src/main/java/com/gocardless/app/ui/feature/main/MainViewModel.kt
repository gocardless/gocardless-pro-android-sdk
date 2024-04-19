package com.gocardless.app.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.Links
import com.gocardless.gocardlesssdk.model.MandateConstraints
import com.gocardless.gocardlesssdk.model.MandateRequest
import com.gocardless.gocardlesssdk.model.PaymentRequest
import com.gocardless.gocardlesssdk.model.Period
import com.gocardless.gocardlesssdk.model.PeriodicLimit
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainUiState {
    object Init : MainUiState()
    object Loading : MainUiState()
    data class Success(val billingRequestFlow: BillingRequestFlow) : MainUiState()
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
                    amount = 1,
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
                        periodicLimits = listOf(PeriodicLimit(
                            period = Period.MONTH,
                            maxTotalAmount = 100,
                        ))
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
                        is ApiSuccess -> _uiState.value = MainUiState.Success(brfResponse.value)
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
}
