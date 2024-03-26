package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.ActionStatus
import com.gocardless.gocardlesssdk.model.ActionType
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.BillingRequestStatus
import com.gocardless.gocardlesssdk.model.CollectBankAccount
import com.gocardless.gocardlesssdk.model.CollectCustomerDetailsRequest
import com.gocardless.gocardlesssdk.model.Customer
import com.gocardless.gocardlesssdk.model.CustomerBillingDetail
import com.gocardless.gocardlesssdk.model.MandateRequest
import com.gocardless.gocardlesssdk.model.MandateVerify
import com.gocardless.gocardlesssdk.model.MetaData
import com.gocardless.gocardlesssdk.model.NotificationType
import com.gocardless.gocardlesssdk.model.NotifyActionRequest
import com.gocardless.gocardlesssdk.model.PaymentRequest
import com.gocardless.gocardlesssdk.network.ApiSuccess
import com.gocardless.gocardlesssdk.network.GoCardlessApi
import com.gocardless.gocardlesssdk.util.DateUtil
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import com.gocardless.gocardlesssdk.util.assertRequest
import com.gocardless.gocardlesssdk.util.successResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BillingRequestServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: GoCardlessApi
    private lateinit var service: BillingRequestService
    private val errorMapper = ErrorMapper(TestNetworkManager.gson)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = TestNetworkManager.create(mockWebServer.url("/"), GoCardlessApi::class.java)
        service = BillingRequestService(api, errorMapper)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_create_billing_request_direct_debit_only() = runBlocking {
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
        val result = service.createBillingRequest(billingRequest)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./billing_request/direct_debit_only_request.json",
                "/billing_requests"
            )
            assertEquals(BillingRequestStatus.PENDING, result.value.status)
            assertEquals(null, result.value.paymentRequest)
            assertEquals(
                "CBD000J8BXKD056",
                result.value.links?.customerBillingDetail
            )
            assertEquals(
                DateUtil.create("2024-03-11T17:21:47.334Z"),
                result.value.resources?.customerBillingDetail?.createdAt
            )
            assertEquals("BR731", result.value.metadata?.get("test"))
            assertEquals(
                "mandate-only-br",
                result.value.mandateRequest?.metadata?.get("postman")
            )
            assertEquals(
                "BR731",
                result.value.metadata?.get("test")
            )
        } else {
            throw Exception("Unexpected result")
        }
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
        val result = service.createBillingRequest(billingRequest)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./billing_request/payment_only_request.json",
                "/billing_requests"
            )
            assertEquals(BillingRequestStatus.PENDING, result.value.status)
            assertEquals(null, result.value.mandateRequest)
            assertEquals("Unbranded Wooden Tuna", result.value.paymentRequest?.description)
            assertEquals(
                "CBD000J8BXSXQGK",
                result.value.links?.customerBillingDetail
            )
            assertEquals(
                DateUtil.create("2024-03-11T17:22:11.334Z"),
                result.value.resources?.customerBillingDetail?.createdAt
            )
            assertEquals("BR766", result.value.metadata?.get("test"))
            assertEquals(
                "payment-only-br",
                result.value.paymentRequest?.metadata?.get("postman")
            )
            assertEquals(
                "BR766",
                result.value.metadata?.get("test")
            )
        } else {
            throw Exception("Unexpected result")
        }
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
        val result = service.createBillingRequest(billingRequest)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./billing_request/dual_flow_request.json",
                "/billing_requests"
            )
            assertEquals(BillingRequestStatus.PENDING, result.value.status)

            assertEquals("bacs", result.value.mandateRequest?.scheme)
            assertEquals(
                "payment-mandate-br",
                result.value.mandateRequest?.metadata?.get("postman")
            )
            assertEquals("Awesome Frozen Pants", result.value.paymentRequest?.description)
            assertEquals(
                "payment-mandate-br",
                result.value.paymentRequest?.metadata?.get("postman")
            )

            assertEquals(
                "CBD000J8BYAEFK1",
                result.value.links?.customerBillingDetail
            )
            assertEquals(
                DateUtil.create("2024-03-11T17:22:40.334Z"),
                result.value.resources?.customerBillingDetail?.createdAt
            )
            assertEquals("BR332", result.value.metadata?.get("test"))

            assertEquals(
                "BR332",
                result.value.metadata?.get("test")
            )
        } else {
            throw Exception("Unexpected result")
        }
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
        val result = service.collectBankAccount(
            billingRequestId,
            bankAccount,
        )

        // Then
        if (result is ApiSuccess) {
            val collectBankAccount =
                result.value.actions.first { it.type == ActionType.COLLECT_BANK_ACCOUNT }
            mockWebServer.assertRequest(
                "./actions/collect_bank_account_request.json",
                "/billing_requests/$billingRequestId/actions/collect_bank_account"
            )
            assertEquals("BRQ00019RM2676C", result.value.id)
            assertEquals(ActionStatus.Completed, collectBankAccount.status)
            assertEquals(listOf(ActionType.COLLECT_AMOUNT), collectBankAccount.requiresActions)
            assertEquals(listOf(ActionType.CHOOSE_CURRENCY), collectBankAccount.completesActions)
            assertEquals(listOf("GB"), collectBankAccount.availableCountryCodes)
        } else {
            throw Exception("Unexpected result")
        }
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
        val result = service.collectCustomerDetails(
            billingRequestId,
            customerDetailsRequest,
        )

        // Then
        if (result is ApiSuccess) {
            val collectCustomerDetails =
                result.value.actions.first { it.type == ActionType.COLLECT_CUSTOMER_DETAILS }
            mockWebServer.assertRequest(
                "./actions/collect_customer_details_request.json",
                "/billing_requests/$billingRequestId/actions/collect_customer_details"
            )
            assertEquals("BRQ00019RM2676C", result.value.id)
            assertEquals(ActionStatus.Completed, collectCustomerDetails.status)
            assertEquals(
                listOf(ActionType.CHOOSE_CURRENCY, ActionType.COLLECT_AMOUNT),
                collectCustomerDetails.requiresActions
            )
            assertEquals(emptyList<ActionType>(), collectCustomerDetails.completesActions)
            assertEquals(emptyList<String>(), collectCustomerDetails.availableCountryCodes)
        } else {
            throw Exception("Unexpected result")
        }
    }

    @Test
    fun test_billing_request_action_confirm_payer_details() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/confirm_payer_details.json")
        val billingRequestId = "BRQ00019S3HW4AA"

        // When
        val result = service.confirmPayerDetails(billingRequestId)

        // Then
        if (result is ApiSuccess) {
            val confirmAction =
                result.value.actions.first { it.type == ActionType.CONFIRM_PAYER_DETAILS }
            assertEquals(
                "/billing_requests/$billingRequestId/actions/confirm_payer_details",
                mockWebServer.takeRequest().path
            )
            Assert.assertTrue(confirmAction.required == true)
            assertEquals("BRQ00019S3HW4AA", result.value.id)
            assertEquals(ActionStatus.Completed, confirmAction.status)
            assertEquals(
                listOf(ActionType.COLLECT_CUSTOMER_DETAILS, ActionType.COLLECT_BANK_ACCOUNT),
                confirmAction.requiresActions
            )
            assertEquals(emptyList<ActionType>(), confirmAction.completesActions)
            assertEquals(emptyList<String>(), confirmAction.availableCountryCodes)
        } else {
            throw Exception("Unexpected result")
        }
    }

    @Test
    fun test_billing_request_action_fulfil() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/fulfil.json")
        val billingRequestId = "BRQ00019RNXYJ5D"

        // When
        val result = service.fulfil(billingRequestId)

        // Then
        if (result is ApiSuccess) {
            assertEquals(
                "/billing_requests/$billingRequestId/actions/fulfil",
                mockWebServer.takeRequest().path
            )
            assertEquals("BRQ00019RNXYJ5D", result.value.id)
            assertEquals(BillingRequestStatus.FULFILLED, result.value.status)
        } else {
            throw Exception("Unexpected result")
        }
    }

    @Test
    fun test_billing_request_action_cancel() = runBlocking {
        // Given
        mockWebServer.successResponse("./actions/cancel.json")
        val billingRequestId = "BRQ0005XEEVPV4B"

        // When
        val result = service.cancel(billingRequestId)


        // Then
        if (result is ApiSuccess) {
            assertEquals(
                "/billing_requests/$billingRequestId/actions/cancel",
                mockWebServer.takeRequest().path
            )
            assertEquals(billingRequestId, result.value.id)
            assertEquals(BillingRequestStatus.CANCELLED, result.value.status)
        } else {
            throw Exception("Unexpected result")
        }
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
        val result = service.notify(billingRequestId, notifyActionRequest)

        // Then
        if (result is ApiSuccess) {
            mockWebServer.assertRequest(
                "./actions/notify_request.json",
                "/billing_requests/$billingRequestId/actions/notify"
            )
            assertEquals("BRQ0005QQ30QYJE", result.value.id)
        } else {
            throw Exception("Unexpected result")
        }
    }
}