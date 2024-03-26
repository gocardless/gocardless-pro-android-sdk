package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.model.ActionStatus
import com.gocardless.gocardlesssdk.model.ActionType
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestActionType
import com.gocardless.gocardlesssdk.model.BillingRequestStatus
import com.gocardless.gocardlesssdk.model.BillingRequestWrapper
import com.gocardless.gocardlesssdk.model.CollectBankAccount
import com.gocardless.gocardlesssdk.model.CollectCustomerDetailsRequest
import com.gocardless.gocardlesssdk.model.Customer
import com.gocardless.gocardlesssdk.model.CustomerBillingDetail
import com.gocardless.gocardlesssdk.model.GenericRequest
import com.gocardless.gocardlesssdk.model.MandateRequest
import com.gocardless.gocardlesssdk.model.MandateVerify
import com.gocardless.gocardlesssdk.model.MetaData
import com.gocardless.gocardlesssdk.model.NotificationType
import com.gocardless.gocardlesssdk.model.NotifyActionRequest
import com.gocardless.gocardlesssdk.model.PaymentRequest
import com.gocardless.gocardlesssdk.util.DateUtil
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.assertRequest
import com.gocardless.gocardlesssdk.util.errorResponse
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class BillingRequestApiTest {
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
    fun test_billing_request_success() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/success.json")

        // When
        val result = api.billingRequests(BillingRequestWrapper())
        val billingRequest = result.body()

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(BillingRequestStatus.PENDING, billingRequest?.billingRequests?.status)
        assertEquals(92368, billingRequest?.billingRequests?.paymentRequest?.amount)
        assertEquals("GBP", billingRequest?.billingRequests?.paymentRequest?.currency)
        assertEquals(
            "CBD000HX9F4HFGQ",
            billingRequest?.billingRequests?.links?.customerBillingDetail
        )
        assertEquals(
            DateUtil.create("2024-02-21T13:54:18.155Z"),
            billingRequest?.billingRequests?.resources?.customerBillingDetail?.createdAt
        )
        assertEquals("BR668", billingRequest?.billingRequests?.metadata?.get("test"))
        assertEquals("value", billingRequest?.billingRequests?.mandateRequest?.metadata?.get("key"))
    }

    @Test
    fun test_billing_request_direct_debit_only() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/direct_debit_only.json")
        val billingRequest = BillingRequest(
            mandateRequest = MandateRequest(
                currency = "GBP",
                scheme = "bacs",
                metadata = MetaData().apply {
                    put("postman", "mandate-only-br")
                }
            ),
            metadata = MetaData().apply {
                put("test", "BR731")
            }
        )

        // When
        val result = api.billingRequests(BillingRequestWrapper(billingRequest))
        val body = result.body()

        // Then
        mockWebServer.assertRequest(
            "./billing_request/direct_debit_only_request.json",
            "/billing_requests"
        )
        assertTrue(result.isSuccessful)
        assertEquals(BillingRequestStatus.PENDING, body?.billingRequests?.status)
        assertEquals(null, body?.billingRequests?.paymentRequest)
        assertEquals(
            "CBD000J8BXKD056",
            body?.billingRequests?.links?.customerBillingDetail
        )
        assertEquals(
            DateUtil.create("2024-03-11T17:21:47.334Z"),
            body?.billingRequests?.resources?.customerBillingDetail?.createdAt
        )
        assertEquals("BR731", body?.billingRequests?.metadata?.get("test"))
        assertEquals(
            "mandate-only-br",
            body?.billingRequests?.mandateRequest?.metadata?.get("postman")
        )
        assertEquals(
            "BR731",
            body?.billingRequests?.metadata?.get("test")
        )
    }

    @Test
    fun test_billing_request_payment_only() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/payment_only.json")
        val billingRequest = BillingRequest(
            paymentRequest = PaymentRequest(
                description = "Unbranded Wooden Tuna",
                amount = 4254,
                currency = "GBP",
                metadata = MetaData().apply {
                    put("postman", "payment-only-br")
                }
            ),
            metadata = MetaData().apply {
                put("test", "BR766")
            }
        )

        // When
        val result = api.billingRequests(BillingRequestWrapper(billingRequest))
        val body = result.body()

        // Then
        mockWebServer.assertRequest(
            "./billing_request/payment_only_request.json",
            "/billing_requests"
        )
        assertTrue(result.isSuccessful)
        assertEquals(BillingRequestStatus.PENDING, body?.billingRequests?.status)
        assertEquals(null, body?.billingRequests?.mandateRequest)
        assertEquals("Unbranded Wooden Tuna", body?.billingRequests?.paymentRequest?.description)
        assertEquals(
            "CBD000J8BXSXQGK",
            body?.billingRequests?.links?.customerBillingDetail
        )
        assertEquals(
            DateUtil.create("2024-03-11T17:22:11.334Z"),
            body?.billingRequests?.resources?.customerBillingDetail?.createdAt
        )
        assertEquals("BR766", body?.billingRequests?.metadata?.get("test"))
        assertEquals(
            "payment-only-br",
            body?.billingRequests?.paymentRequest?.metadata?.get("postman")
        )
        assertEquals(
            "BR766",
            body?.billingRequests?.metadata?.get("test")
        )
    }

    @Test
    fun test_billing_request_dual_flow() = runBlocking {
        // Given
        mockWebServer.successResponse("./billing_request/dual_flow.json")
        val billingRequest = BillingRequest(
            paymentRequest = PaymentRequest(
                description = "Awesome Frozen Pants",
                amount = 23956,
                currency = "GBP",
                metadata = MetaData().apply {
                    put("postman", "payment-mandate-br")
                }
            ),
            mandateRequest = MandateRequest(
                currency = "GBP",
                scheme = "bacs",
                verify = MandateVerify.RECOMMENDED,
                metadata = MetaData().apply {
                    put("postman", "payment-mandate-br")
                }
            ),
            metadata = MetaData().apply {
                put("test", "BR332")
            }
        )

        // When
        val result = api.billingRequests(BillingRequestWrapper(billingRequest))
        val body = result.body()

        // Then
        mockWebServer.assertRequest(
            "./billing_request/dual_flow_request.json",
            "/billing_requests"
        )
        assertTrue(result.isSuccessful)
        assertEquals(BillingRequestStatus.PENDING, body?.billingRequests?.status)

        assertEquals("bacs", body?.billingRequests?.mandateRequest?.scheme)
        assertEquals(
            "payment-mandate-br",
            body?.billingRequests?.mandateRequest?.metadata?.get("postman")
        )
        assertEquals("Awesome Frozen Pants", body?.billingRequests?.paymentRequest?.description)
        assertEquals(
            "payment-mandate-br",
            body?.billingRequests?.paymentRequest?.metadata?.get("postman")
        )

        assertEquals(
            "CBD000J8BYAEFK1",
            body?.billingRequests?.links?.customerBillingDetail
        )
        assertEquals(
            DateUtil.create("2024-03-11T17:22:40.334Z"),
            body?.billingRequests?.resources?.customerBillingDetail?.createdAt
        )
        assertEquals("BR332", body?.billingRequests?.metadata?.get("test"))

        assertEquals(
            "BR332",
            body?.billingRequests?.metadata?.get("test")
        )
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
    }

    @Test
    fun test_billing_request_action_collect_bank_account() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/collect_bank_account.json")
        val billingRequestId = "BRQ00019RM2676C"
        val bankAccount = CollectBankAccount(
            countryCode = "GB",
            accountHolderName = "INVESTMENT ACCOUNT",
            accountNumber = "55779911",
            branchCode = "200000",
            metadata = MetaData().apply {
                put("name", "Investment Account")
            }
        )

        // When
        val result = api.billingRequestsActions(
            GenericRequest(bankAccount),
            billingRequestId,
            BillingRequestActionType.CollectBankAccount.value
        )
        val billingRequest = result.body()
        val collectBankAccount =
            billingRequest?.billingRequests?.actions?.first { it.type == ActionType.COLLECT_BANK_ACCOUNT }

        // Then
        mockWebServer.assertRequest(
            "./actions/collect_bank_account_request.json",
            "/billing_requests/$billingRequestId/actions/collect_bank_account"
        )
        assertTrue(result.isSuccessful)
        assertEquals("BRQ00019RM2676C", billingRequest?.billingRequests?.id)
        assertEquals(ActionStatus.Completed, collectBankAccount?.status)
        assertEquals(listOf(ActionType.COLLECT_AMOUNT), collectBankAccount?.requiresActions)
        assertEquals(listOf(ActionType.CHOOSE_CURRENCY), collectBankAccount?.completesActions)
        assertEquals(listOf("GB"), collectBankAccount?.availableCountryCodes)
    }

    @Test
    fun test_billing_request_action_collect_customer_details() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/collect_customer_details.json")
        val billingRequestId = "BRQ00019RM2676C"
        val customerDetailsRequest = CollectCustomerDetailsRequest(
            customer = Customer(
                email = "monica.hodkiewicz40@example.org",
                companyName = "Trantow Group",
                givenName = "Howell",
                familyName = "Murazik",
                phoneNumber = "+16175551212",
            ),
            customerBillingDetail = CustomerBillingDetail(
                addressLine1 = "1350 Dare Port",
                city = "Julianaport",
                postalCode = "E8 3GX",
                countryCode = "GB",
            )
        )

        // When
        val result = api.billingRequestsActions(
            GenericRequest(customerDetailsRequest),
            billingRequestId,
            BillingRequestActionType.CollectCustomerDetails.value
        )
        val billingRequest = result.body()
        val collectCustomerDetails =
            billingRequest?.billingRequests?.actions?.first { it.type == ActionType.COLLECT_CUSTOMER_DETAILS }

        // Then
        mockWebServer.assertRequest(
            "./actions/collect_customer_details_request.json",
            "/billing_requests/$billingRequestId/actions/collect_customer_details"
        )
        assertTrue(result.isSuccessful)
        assertEquals("BRQ00019RM2676C", billingRequest?.billingRequests?.id)
        assertEquals(ActionStatus.Completed, collectCustomerDetails?.status)
        assertEquals(
            listOf(ActionType.CHOOSE_CURRENCY, ActionType.COLLECT_AMOUNT),
            collectCustomerDetails?.requiresActions
        )
        assertEquals(emptyList<ActionType>(), collectCustomerDetails?.completesActions)
        assertEquals(emptyList<String>(), collectCustomerDetails?.availableCountryCodes)
    }

    @Test
    fun test_billing_request_action_confirm_payer_details() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/confirm_payer_details.json")
        val billingRequestId = "BRQ00019S3HW4AA"

        // When
        val result = api.billingRequestsActions(
            GenericRequest(),
            billingRequestId,
            BillingRequestActionType.ConfirmPayerDetails.value
        )
        val billingRequest = result.body()
        val confirmAction =
            billingRequest?.billingRequests?.actions?.first { it.type == ActionType.CONFIRM_PAYER_DETAILS }

        // Then
        assertEquals(
            "/billing_requests/$billingRequestId/actions/confirm_payer_details",
            mockWebServer.takeRequest().path
        )
        assertTrue(result.isSuccessful)
        assertTrue(confirmAction?.required == true)
        assertEquals("BRQ00019S3HW4AA", billingRequest?.billingRequests?.id)
        assertEquals(ActionStatus.Completed, confirmAction?.status)
        assertEquals(
            listOf(ActionType.COLLECT_CUSTOMER_DETAILS, ActionType.COLLECT_BANK_ACCOUNT),
            confirmAction?.requiresActions
        )
        assertEquals(emptyList<ActionType>(), confirmAction?.completesActions)
        assertEquals(emptyList<String>(), confirmAction?.availableCountryCodes)
    }

    @Test
    fun test_billing_request_action_fulfil() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/fulfil.json")
        val billingRequestId = "BRQ00019RNXYJ5D"

        // When
        val result = api.billingRequestsActions(
            GenericRequest(),
            billingRequestId,
            BillingRequestActionType.Fulfil.value
        )
        val billingRequest = result.body()

        // Then
        assertEquals(
            "/billing_requests/$billingRequestId/actions/fulfil",
            mockWebServer.takeRequest().path
        )
        assertTrue(result.isSuccessful)
        assertEquals("BRQ00019RNXYJ5D", billingRequest?.billingRequests?.id)
    }

    @Test
    fun test_billing_request_action_cancel() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/cancel.json")
        val billingRequestId = "BRQ0005XEEVPV4B"

        // When
        val result = api.billingRequestsActions(
            GenericRequest(),
            billingRequestId,
            BillingRequestActionType.Cancel.value
        )
        val billingRequest = result.body()

        // Then
        assertEquals(
            "/billing_requests/$billingRequestId/actions/cancel",
            mockWebServer.takeRequest().path
        )
        assertTrue(result.isSuccessful)
        assertEquals("BRQ0005XEEVPV4B", billingRequest?.billingRequests?.id)
    }

    @Test
    fun test_billing_request_action_notify() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/notify.json")
        val billingRequestId = "BRQ0005QQ30QYJE"
        val notifyActionRequest = NotifyActionRequest(
            notificationType = NotificationType.Email,
            redirectUri = "https://example.com"
        )

        // When
        val result = api.billingRequestsActions(
            GenericRequest(notifyActionRequest),
            billingRequestId,
            BillingRequestActionType.Notify.value
        )
        val billingRequest = result.body()

        // Then
        mockWebServer.assertRequest(
            "./actions/notify_request.json",
            "/billing_requests/$billingRequestId/actions/notify"
        )
        assertTrue(result.isSuccessful)
        assertEquals("BRQ0005QQ30QYJE", billingRequest?.billingRequests?.id)
    }
}