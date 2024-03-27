package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class CollectCustomerDetailsRequest(
    @SerializedName("customer")
    var customer: Customer? = null,
    @SerializedName("customer_billing_detail")
    var customerBillingDetail: CustomerBillingDetail? = null,
)