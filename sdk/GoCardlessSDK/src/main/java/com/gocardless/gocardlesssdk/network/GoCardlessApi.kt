package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestBundle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoCardlessApi {
    @POST("billing_requests")
    suspend fun billingRequests(@Body request: BillingRequestBundle): Response<BillingRequestBundle>

//    @GET("customers")
//    suspend fun all(): Response<Customers>
//
//    @DELETE("customers/{customerId}")
//    suspend fun delete(@Path("customerId") customerId: String): Response<Customers>
}