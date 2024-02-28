package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
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