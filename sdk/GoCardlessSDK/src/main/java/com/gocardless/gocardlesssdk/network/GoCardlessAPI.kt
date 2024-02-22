package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.Customers
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoCardlessAPI {
    @POST("billing_requests")
    suspend fun billingRequests(request: BillingRequest): Response<BillingRequest>

    @GET("customers")
    suspend fun all(): Response<Customers>

    @DELETE("customers/{customerId}")
    suspend fun delete(@Path("customerId") customerId: String): Response<Customers>
}