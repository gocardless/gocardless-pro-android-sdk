package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class BillingRequestBundle(
    @SerializedName("billing_requests") var billingRequests: BillingRequests? = BillingRequests()
)