package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.Payment
import com.gocardless.gocardlesssdk.model.PaymentLinks
import com.gocardless.gocardlesssdk.model.PaymentStatus
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.assertPath
import com.gocardless.gocardlesssdk.util.assertRequest
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PaymentServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: GoCardlessApi
    private lateinit var service: PaymentService
    private val errorMapper = ErrorMapper(TestNetworkManager.gson)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = TestNetworkManager.create(mockWebServer.url("/"), GoCardlessApi::class.java)
        service = PaymentService(api, errorMapper)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_get_payment_request() = runBlocking {
        // Given
        val paymentRequestId = "PM009ME5BM6SR3"
        mockWebServer.successResponse("./payments/success.json")

        // When
        val result = service.getPayment(paymentRequestId)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertPath("/payments/${paymentRequestId}")
            assertEquals(paymentRequestId, result.value.id)
            assertEquals(33197, result.value.amount)
            assertEquals(PaymentStatus.PendingSubmission, result.value.status)
        } else {
            throw Exception("Unexpected result")
        }
    }

    @Test
    fun test_create_payment() = runBlocking {
        // Given
        val payment = Payment(
            amount = 123,
            retryIfPossible = true,
            links = PaymentLinks(
                mandate = "MD000ZQFRW73MD",
                creditor = "CR00007J0CSSJG"
            )
        )
        mockWebServer.successResponse("./payments/success.json")

        // When
        val result = service.createPaymentRequest(payment)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./payments/request.json",
                "/payments"
            )
            assertEquals("PM009ME5BM6SR3", result.value.id)
            assertEquals("MD000ZQFRW73MD", result.value.links?.mandate)
        } else {
            throw Exception("Unexpected result")
        }
    }
}