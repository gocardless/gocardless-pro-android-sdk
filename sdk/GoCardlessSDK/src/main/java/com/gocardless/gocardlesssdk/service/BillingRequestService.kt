package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.model.Cursors
import com.gocardless.gocardlesssdk.model.Customers
import com.gocardless.gocardlesssdk.model.Meta
import com.gocardless.gocardlesssdk.network.Error
import com.gocardless.gocardlesssdk.network.GoCardlessAPI
import com.gocardless.gocardlesssdk.network.NetworkResult
import com.gocardless.gocardlesssdk.network.Success

/**
 * A Billing Request enables you to collect all types of GoCardless payments using
 * the Billing Request Flow API. This includes both one-off and recurring payments
 * from your new or existing customers.
 */
class BillingRequestService(private val goCardlessAPI: GoCardlessAPI) {
    suspend fun createBillingRequest(): NetworkResult<Customers> {
        val response = goCardlessAPI.all()

        return if (response.isSuccessful) {
            Success(response.body() ?: Customers(emptyList(), Meta(Cursors(null, null), 0)))
        } else {
            Error(response.errorBody()?.toString() ?: "")
        }
    }
}