package com.gocardless.app.ui.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.Customer
import com.gocardless.gocardlesssdk.model.Links
import com.gocardless.gocardlesssdk.model.PaymentRequest
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiResult
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
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {

            val brf = createBRF(
                BillingRequest(
                    paymentRequest = PaymentRequest(
                        currency = "GBP",
                        amount = 1,
                        description = "Description"
                    )
                )
            )

            if (brf != null) {
                _uiState.value = MainUiState.Success(brf)
            } else {
                _uiState.value = MainUiState.Error("Error creating Billing Request")
            }
        }
    }

    private suspend fun createBRF(br: BillingRequest): BillingRequestFlow? {
        val brResponse = GoCardlessSDK.billingRequestService.createBillingRequest(br)
        if (brResponse is ApiSuccess) {
            val brfResponse =
                GoCardlessSDK.billingRequestFlowService.createBillingRequestFlow(
                    BillingRequestFlow(
                        links = Links(billingRequest = brResponse.value.id)
                    )
                )
            return if (brfResponse is ApiSuccess) brfResponse.value else null
        }
        return null
    }
}
