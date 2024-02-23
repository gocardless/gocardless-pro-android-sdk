package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BillingRequests(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("mandate_request") var mandateRequest: MandateRequest? = MandateRequest(),
    @SerializedName("payment_request") var paymentRequest: PaymentRequest? = PaymentRequest(),
    @SerializedName("metadata") var metadata: MetaData? = MetaData(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("fallback_enabled") var fallbackEnabled: Boolean? = null,
    @SerializedName("fallback_occurred") var fallbackOccurred: Boolean? = null,
    @SerializedName("sign_flow_url") var signFlowUrl: String? = null,
    @SerializedName("creditor_name") var creditorName: String? = null,
    @SerializedName("actions") var actions: ArrayList<Actions> = arrayListOf(),
    @SerializedName("resources") var resources: Resources? = Resources(),
    @SerializedName("experimentation") var experimentation: Experimentation? = Experimentation()
)