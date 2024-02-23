package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("customer") var customer: String? = null,
    @SerializedName("customer_billing_detail") var customerBillingDetail: String? = null,
    @SerializedName("creditor") var creditor: String? = null,
    @SerializedName("organisation") var organisation: String? = null,
    @SerializedName("payment_request") var paymentRequest: String? = null,
    @SerializedName("mandate_request") var mandateRequest: String? = null
)