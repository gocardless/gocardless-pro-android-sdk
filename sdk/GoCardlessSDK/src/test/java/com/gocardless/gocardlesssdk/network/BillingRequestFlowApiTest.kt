package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestStatus
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.errorResponse
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
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
        mockWebServer.successResponse("./billing_request/success.json")

        // When
        val result = api.billingRequests(BillingRequestWrapper())

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(BillingRequestStatus.PENDING, result.body()?.billingRequests?.status)
    }

    @Test
    fun test_billing_request_success_unknown_status() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/success_unknown.json")

        // When
        val result = api.billingRequests(BillingRequestWrapper())

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(null, result.body()?.billingRequests?.status)
    }

    @Test
    fun test_billing_request_error() = runBlocking {
        // Given
        mockWebServer.errorResponse("./billing_request/error.json")

        // When
        val result = api.billingRequests(BillingRequestWrapper())

        // Then
        assertFalse(result.isSuccessful)
        assertNotEquals(BillingRequestStatus.PENDING, result.body()?.billingRequests?.status)
    }
}