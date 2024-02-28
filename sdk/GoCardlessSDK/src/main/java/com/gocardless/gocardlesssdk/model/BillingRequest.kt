package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BillingRequest(
    /**
     * Unique identifier, beginning with "BRQ".
     */
    @SerializedName("id") var id: String? = null,
    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    @SerializedName("created_at") var createdAt: Date? = null,
    /**
     * One of:
     * `pending`: the billing request is pending and can be used
     * `ready_to_fulfil`: the billing request is ready to fulfil
     * `fulfilling`: the billing request is currently undergoing fulfilment
     * `fulfilled`: the billing request has been fulfilled and a payment created
     * `cancelled`: the billing request has been cancelled and cannot be used
     */
    @SerializedName("status") var status: BillingRequestStatus? = BillingRequestStatus.UNKNOWN,
    /**
     * Request for a mandate
     */
    @SerializedName("mandate_request") var mandateRequest: MandateRequest? = MandateRequest(),
    /**
     * Request for a one-off strongly authorised payment
     */
    @SerializedName("payment_request") var paymentRequest: PaymentRequest? = PaymentRequest(),
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    @SerializedName("metadata") var metadata: MetaData? = MetaData(),
    @SerializedName("links") var links: Links? = Links(),
    /**
     * (Optional) If true, this billing request can fallback from instant payment to direct debit.
     * Should not be set if GoCardless payment intelligence feature is used.
     *
     * See [Billing Requests: Retain customers with
     * Fallbacks](https://developer.gocardless.com/billing-requests/retain-customers-with-fallbacks/)
     * for more information.
     */
    @SerializedName("fallback_enabled") var fallbackEnabled: Boolean? = null,
    /**
     * Once the fallback occurs, then the fallback_occurred the parameter will be set to `true``
     */
    @SerializedName("fallback_occurred") var fallbackOccurred: Boolean? = null,
    @SerializedName("sign_flow_url") var signFlowUrl: String? = null,
    @SerializedName("creditor_name") var creditorName: String? = null,
    /**
     * List of actions that can be performed before this billing request can be fulfilled.
     */
    @SerializedName("actions") var actions: ArrayList<Action> = arrayListOf(),
    @SerializedName("resources") var resources: Resources? = Resources(),
)

/**
 * One of:
 * `pending`: the billing request is pending and can be used
 * `ready_to_fulfil`: the billing request is ready to fulfil
 * `fulfilling`: the billing request is currently undergoing fulfilment
 * `fulfilled`: the billing request has been fulfilled and a payment created
 * `cancelled`: the billing request has been cancelled and cannot be used
 */
enum class BillingRequestStatus {
    @SerializedName("pending")
    PENDING,

    @SerializedName("ready_to_fulfil")
    READY_TO_FULFIL,

    @SerializedName("fulfilling")
    FULFILLING,

    @SerializedName("fulfilled")
    FULFILLED,

    @SerializedName("cancelled")
    CANCELLED,

    @SerializedName("unknown")
    UNKNOWN
}