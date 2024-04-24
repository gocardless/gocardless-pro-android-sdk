package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.BillingRequestFlowWrapper
import com.gocardless.gocardlesssdk.model.Links
import com.gocardless.gocardlesssdk.util.DateUtil
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.assertRequest
import com.gocardless.gocardlesssdk.util.errorResponse
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BillingRequestFlowApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: GoCardlessApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = TestNetworkManager.create(mockWebServer.url("/"), GoCardlessApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_billing_request_flow_success() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request_flow/success.json")
        val billingRequestFlow = BillingRequestFlow(
            autoFulfil = true,
            lockCurrency = true,
            lockBankAccount = false,
            lockCustomerDetails = false,
            redirectUri = "https://gocardless.com/",
            showRedirectButtons = true,
            exitUri = "https://developer.gocardless.com/",
            links = Links(
                billingRequest = "BRQ0005VSWHP5JE"
            )
        )

        // When
        val result = api.billingRequestFlow(BillingRequestFlowWrapper(billingRequestFlow))
        val body = result.body()

        // Then
        mockWebServer.assertRequest(
            "./billing_request_flow/success_request.json",
            "/billing_flow_requests"
        )
        assertEquals(true, result.isSuccessful)
        assertEquals("BRF0002HJNHHDSCF4F5XEJE61E94AMAX", body?.billingRequestFlow?.id)
        assertEquals("BRQ0005VSWHP5JE", body?.billingRequestFlow?.links?.billingRequest)
        assertEquals("https://gocardless.com/", body?.billingRequestFlow?.redirectUri)
        assertEquals("https://developer.gocardless.com/", body?.billingRequestFlow?.exitUri)
        assertEquals(
            DateUtil.create("2024-03-11T20:21:45.529Z"),
            body?.billingRequestFlow?.createdAt
        )
        assertEquals(false, body?.billingRequestFlow?.lockCustomerDetails)
    }

    @Test
    fun test_create_billing_request_flow_missing_field() = runBlocking {
        // Given no billing request id is provided
        mockWebServer.errorResponse("./billing_request_flow/error.json")
        val billingRequestFlow = BillingRequestFlow(
            autoFulfil = true,
            lockCurrency = true,
            lockBankAccount = false,
            lockCustomerDetails = false,
            redirectUri = "https://gocardless.com/",
            showRedirectButtons = true,
            exitUri = "https://developer.gocardless.com/",
            links = Links()
        )

        // When
        val result = api.billingRequestFlow(BillingRequestFlowWrapper(billingRequestFlow))
        val body = result.body()

        // Then
        assertEquals(false, result.isSuccessful)
    }
}