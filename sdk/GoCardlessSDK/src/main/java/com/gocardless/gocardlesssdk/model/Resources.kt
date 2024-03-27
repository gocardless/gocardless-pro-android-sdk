package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Resources(
    @SerializedName("customer") var customer: Customer? = Customer(),
    @SerializedName("customer_billing_detail") var customerBillingDetail: CustomerBillingDetail? = CustomerBillingDetail()
)