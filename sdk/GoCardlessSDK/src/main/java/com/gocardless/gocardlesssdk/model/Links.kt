package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Links(
    /**
     * ID of the [customer](#core-endpoints-customers) that will be used for this request
     */
    @SerializedName("customer") var customer: String? = null,
    /**
     * ID of the customer billing detail that will be used for this request
     */
    @SerializedName("customer_billing_detail") var customerBillingDetail: String? = null,
    /**
     * ID of the associated [creditor](#core-endpoints-creditors).
     */
    @SerializedName("creditor") var creditor: String? = null,
    /**
     * ID of the associated organisation.
     */
    @SerializedName("organisation") var organisation: String? = null,
    /**
     * (Optional) ID of the associated payment request
     */
    @SerializedName("payment_request") var paymentRequest: String? = null,
    /**
     * (Optional) ID of the associated mandate request
     */
    @SerializedName("mandate_request") var mandateRequest: String? = null,
    /**
     * ID of the billing request against which this flow was created.
     */
    @SerializedName("billing_request") var billingRequest: String? = null
)