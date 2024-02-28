package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GoCardlessApi {
    @POST("billing_requests")
    suspend fun billingRequests(@Body request: BillingRequestWrapper): Response<BillingRequestWrapper>

    @POST("billing_flow_requests")
    suspend fun billingFlowRequests(@Body request: BillingRequestWrapper): Response<BillingRequestWrapper>
}