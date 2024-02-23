package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.model.BillingRequestBundle
import com.gocardless.gocardlesssdk.network.Error
import com.gocardless.gocardlesssdk.network.GoCardlessApi
import com.gocardless.gocardlesssdk.network.NetworkResult
import com.gocardless.gocardlesssdk.network.Success

/**
 * A Billing Request enables you to collect all types of GoCardless payments using
 * the Billing Request Flow API. This includes both one-off and recurring payments
 * from your new or existing customers.
 */
class BillingRequestService(private val goCardlessAPI: GoCardlessApi) {
    suspend fun createBillingRequest(billingRequest: BillingRequestBundle): NetworkResult<BillingRequestBundle> {
        val response = goCardlessAPI.billingRequests(billingRequest)

        return if (response.isSuccessful) {
            Success(response.body()!!)
        } else {
            Error(response.errorBody()?.toString() ?: "")
        }
    }
}