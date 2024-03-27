package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class BillingRequestFlowWrapper(
    @SerializedName("billing_request_flows") var billingRequestFlow: BillingRequestFlow? = BillingRequestFlow()
)