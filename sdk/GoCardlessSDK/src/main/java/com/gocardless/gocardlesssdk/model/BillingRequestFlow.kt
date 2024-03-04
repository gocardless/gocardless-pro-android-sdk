package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BillingRequestFlow(
    @SerializedName("id") var id: String? = null,
    @SerializedName("auto_fulfil") var autoFulfil: Boolean? = null,
    @SerializedName("redirect_uri") var redirectUri: String? = null,
    @SerializedName("exit_uri") var exitUri: String? = null,
    @SerializedName("authorisation_url") var authorisationUrl: String? = null,
    @SerializedName("lock_customer_details") var lockCustomerDetails: Boolean? = null,
    @SerializedName("lock_bank_account") var lockBankAccount: Boolean? = null,
    @SerializedName("session_token") var sessionToken: String? = null,
    @SerializedName("expires_at") var expiresAt: Date? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("config") var config: Config? = Config(),
    @SerializedName("redirect_flow_id") var redirectFlowId: String? = null,
    @SerializedName("show_redirect_buttons") var showRedirectButtons: Boolean? = null,
    @SerializedName("lock_currency") var lockCurrency: Boolean? = null,
    @SerializedName("prefilled_customer") var prefilledCustomer: String? = null,
    @SerializedName("prefilled_bank_account") var prefilledBankAccount: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("show_success_redirect_button") var showSuccessRedirectButton: Boolean? = null,
    @SerializedName("customer_details_captured") var customerDetailsCaptured: Boolean? = null,
    @SerializedName("redirect_origin") var redirectOrigin: String? = null
)