package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class MandateRequest(
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("constraints") var constraints: String? = null,
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("sweeping") var sweeping: Boolean? = null,
    @SerializedName("verify") var verify: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("metadata") var metadata: MetaData? = MetaData(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("payer_requested_dual_signature") var payerRequestedDualSignature: Boolean? = null
)