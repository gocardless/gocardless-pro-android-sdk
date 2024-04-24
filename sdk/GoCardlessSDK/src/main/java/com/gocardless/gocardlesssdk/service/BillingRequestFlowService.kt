package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.BillingRequestFlowWrapper
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiResult
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi

/**
 * Billing Request Flows can be created to enable a payer to authorise a payment
 * created for a scheme with strong payer authorisation (such as open banking single payments).
 */
class BillingRequestFlowService(
    private val goCardlessAPI: GoCardlessApi,
    private val errorMapper: ErrorMapper,
) {
    /**
     * Creates a new billing request flow.
     */
    suspend fun createBillingRequestFlow(billingRequestFlow: BillingRequestFlow): ApiResult<BillingRequestFlow> {
        val response =
            goCardlessAPI.billingRequestFlow(BillingRequestFlowWrapper(billingRequestFlow))

        return if (response.isSuccessful) {
            ApiSuccess(response.body()?.billingRequestFlow ?: billingRequestFlow)
        } else {
            val error = errorMapper.process(response.code(), response.errorBody()?.charStream())
            ApiError(error)
        }
    }
}