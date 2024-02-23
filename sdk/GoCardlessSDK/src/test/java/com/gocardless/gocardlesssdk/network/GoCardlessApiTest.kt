package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.BillingRequestBundle
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GoCardlessApiTest {
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
    fun test_billing_request() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/success.json")

        // When
        val result = api.billingRequests(BillingRequestBundle())

        // Then
        assertTrue(result.isSuccessful)
    }
}