package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a mandate request resource returned from the API.
 *
 * Request for a mandate
 */
data class MandateRequest(
    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
     */
    @SerializedName("currency") var currency: String? = null,
    /**
     * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo and
     * VRP.
     */
    @SerializedName("constraints") var constraints: MandateConstraints? = null,
    /**
     * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
     * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
     * Optional for mandate only requests - if left blank, the payer will be able to select the
     * currency/scheme to pay with from a list of your available schemes.
     */
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("sweeping") var sweeping: Boolean? = null,
    /**
     * Verification preference for the mandate. One of:
     * <ul>
     * <li>`minimum`: only verify if absolutely required, such as when part of scheme rules</li>
     * <li>`recommended`: in addition to `minimum`, use the GoCardless payment intelligence
     * solution to decide if a payer should be verified</li>
     * <li>`when_available`: if verification mechanisms are available, use them</li>
     * <li>`always`: as `when_available`, but fail to create the Billing Request if a mechanism
     * isn't available</li>
     * </ul>
     *
     * By default, all Billing Requests use the `recommended` verification preference. It uses
     * GoCardless payment intelligence solution to determine if a payer is fraudulent or not.
     * The verification mechanism is based on the response and the payer may be asked to verify
     * themselves. If the feature is not available, `recommended` behaves like `minimum`.
     *
     * If you never wish to take advantage of our reduced risk products and Verified Mandates as
     * they are released in new schemes, please use the `minimum` verification preference.
     *
     * See [Billing Requests: Creating Verified
         * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
     * for more information.
     */
    @SerializedName("verify") var verify: MandateVerify? = null,
    @SerializedName("links") var links: Links? = null,
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    @SerializedName("metadata") var metadata: MetaData? = null,
    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     */
    @SerializedName("description") var description: String? = null,
    /**
     * This attribute can be set to true if the payer has indicated that multiple signatures are
     * required for the mandate. As long as every other Billing Request actions have been
     * completed, the payer will receive an email notification containing instructions on how to
     * complete the additional signature. The dual signature flow can only be completed using
     * GoCardless branded pages.
     */
    @SerializedName("payer_requested_dual_signature") var payerRequestedDualSignature: Boolean? = null
)

/**
 * Verification preference for the mandate.
 * See [Billing Requests: Creating Verified
         * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
 * for more information.
 */
enum class MandateVerify {
    @SerializedName("minimum")
    MINIMUM,

    @SerializedName("recommended")
    RECOMMENDED,

    @SerializedName("when_available")
    WHEN_AVAILABLE,

    @SerializedName("always")
    ALWAYS,

    @SerializedName("unknown")
    UNKNOWN
}