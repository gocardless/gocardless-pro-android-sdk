package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a payment request resource returned from the API.
 *
 * Request for a one-off strongly authorised payment
 */
data class PaymentRequest(
    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     *
     */
    @SerializedName("description") var description: String? = null,
    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
     * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
     * Germany only.
     */
    @SerializedName("currency") var currency: String? = null,
    /**
     * Amount in minor unit (e.g. pence in GBP, cents in EUR).
     */
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("min_amount") var minAmount: String? = null,
    @SerializedName("max_amount") var maxAmount: String? = null,
    @SerializedName("default_min_amount") var defaultMinAmount: String? = null,
    @SerializedName("default_max_amount") var defaultMaxAmount: String? = null,
    /**
     * The amount to be deducted from the payment as an app fee, to be paid to the partner
     * integration which created the billing request, in the lowest denomination for the
     * currency (e.g. pence in GBP, cents in EUR).
     */
    @SerializedName("app_fee") var appFee: String? = null,
    /**
     * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
     * supported in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer`
     * are supported in Germany (EUR). In Germany, `sepa_credit_transfer` is used as the
     * default. Please be aware that `sepa_instant_credit_transfer` may incur an additional fee
     * for your customer.
     */
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("links") var links: Links? = null,
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    @SerializedName("metadata") var metadata: MetaData? = null,
    @SerializedName("flexible_amount") var flexibleAmount: Boolean? = null
)