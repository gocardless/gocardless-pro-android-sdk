package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("description") var description: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("min_amount") var minAmount: String? = null,
    @SerializedName("max_amount") var maxAmount: String? = null,
    @SerializedName("default_min_amount") var defaultMinAmount: String? = null,
    @SerializedName("default_max_amount") var defaultMaxAmount: String? = null,
    @SerializedName("app_fee") var appFee: String? = null,
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("metadata") var metadata:MetaData? = MetaData(),
    @SerializedName("flexible_amount") var flexibleAmount: Boolean? = null
)