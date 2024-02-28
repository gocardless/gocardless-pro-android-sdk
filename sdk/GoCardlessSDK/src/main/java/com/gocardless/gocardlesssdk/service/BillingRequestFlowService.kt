package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.GoCardlessApi
import com.gocardless.gocardlesssdk.network.ApiResult
import com.gocardless.gocardlesssdk.network.ApiSuccess

/**
 * Billing Request Flows can be created to enable a payer to authorise a payment
 * created for a scheme with strong payer authorisation (such as open banking single payments).
 */
class BillingRequestFlowService(
    private val goCardlessAPI: GoCardlessApi,
    private val errorMapper: ErrorMapper,
) {
    suspend fun createBillingRequest(billingRequest: BillingRequest): ApiResult<BillingRequest> {
        val response = goCardlessAPI.billingRequests(BillingRequestWrapper(billingRequest))

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequests ?: billingRequest)
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }
}