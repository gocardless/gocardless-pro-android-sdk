package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestFlowWrapper
import com.gocardless.gocardlesssdk.model.BillingRequestList
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import com.gocardless.gocardlesssdk.model.GenericRequest
import com.gocardless.gocardlesssdk.model.PaymentRequestWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoCardlessApi {
    /**
     * Create a Direct Debit mandate using Billing Request
     */
    @POST("billing_requests")
    suspend fun billingRequests(@Body request: BillingRequestWrapper): Response<BillingRequestWrapper>

    /**
     * Create a Billing Request action request
     */
    @POST("billing_requests/{billingRequestId}/actions/{actionName}")
    suspend fun billingRequestsActions(
        @Body request: GenericRequest,
        @Path("billingRequestId") billingRequestId: String,
        @Path("actionName") actionName: String
    ): Response<BillingRequestWrapper>

    /**
     * Create a Billing Request Flow that can be used for your customer to authorise payments
     */
    @POST("billing_flow_requests")
    suspend fun billingFlowRequests(@Body request: BillingRequestFlowWrapper): Response<BillingRequestFlowWrapper>

    /**
     * Fetches a billing request
     */
    @GET("billing_requests/{billingRequestId}")
    suspend fun getBillingRequest(@Path("billingRequestId") billingRequestId: String): Response<BillingRequestWrapper>

    /**
     * Returns a cursor-paginated list of your billing requests.
     */
    @GET("billing_requests")
    suspend fun getBillingRequests(): Response<BillingRequestList>

    /**
     * Creates a new payment object.
     */
    @POST("payments")
    suspend fun createPayment(@Body request: PaymentRequestWrapper): Response<PaymentRequestWrapper>

    /**
     * Retrieves the details of a single existing payment.
     */
    @GET("payments/{paymentRequestId}")
    suspend fun getPaymentRequest(@Path("paymentRequestId") paymentRequestId: String): Response<PaymentRequestWrapper>
}