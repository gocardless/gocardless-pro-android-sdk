package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.error.InvalidApiUsageError
import com.gocardless.gocardlesssdk.model.BillingRequestFlow
import com.gocardless.gocardlesssdk.model.Links
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi
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

class BillingRequestFlowServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: GoCardlessApi
    private lateinit var service: BillingRequestFlowService
    private val errorMapper = ErrorMapper(TestNetworkManager.gson)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = TestNetworkManager.create(mockWebServer.url("/"), GoCardlessApi::class.java)
        service = BillingRequestFlowService(api, errorMapper)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_create_billing_request_flow() = runBlocking {
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
        val result = service.createBillingRequestFlow(billingRequestFlow)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./billing_request_flow/success_request.json",
                "/billing_flow_requests"
            )
            assertEquals("BRF0002HJNHHDSCF4F5XEJE61E94AMAX", result.value.id)
            assertEquals("BRQ0005VSWHP5JE", result.value.links?.billingRequest)
            assertEquals("https://gocardless.com/", result.value.redirectUri)
            assertEquals("https://developer.gocardless.com/", result.value.exitUri)
            assertEquals(DateUtil.create("2024-03-11T20:21:45.529Z"), result.value.createdAt)
            assertEquals(false, result.value.lockCustomerDetails)
        } else {
            throw Exception("Unexpected result")
        }
    }

    @Test
    fun test_create_billing_request_flow_missing_field() = runBlocking {
        // Given no billing request id is provided
        mockWebServer.errorResponse("./billing_request_flow/error.json")
        val billingRequest = BillingRequestFlow(
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
        val result = service.createBillingRequestFlow(billingRequest)

        // Then
        if (result is ApiError) {
            assertEquals(InvalidApiUsageError::class.java, result.error!!::class.java)
        } else {
            throw Exception("Unexpected result")
        }
    }
}