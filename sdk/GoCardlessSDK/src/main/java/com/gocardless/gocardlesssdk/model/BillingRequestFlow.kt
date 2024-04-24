package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BillingRequestFlow(
    @SerializedName("id") var id: String? = null,
    /**
     * Fulfil the Billing Request on completion of the flow (true by default). Disabling the auto_fulfil is not allowed currently.
     */
    @SerializedName("auto_fulfil") var autoFulfil: Boolean? = null,
    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    @SerializedName("redirect_uri") var redirectUri: String? = null,
    /**
     * URL that the payer can be taken to if there isn’t a way to progress ahead in flow.
     */
    @SerializedName("exit_uri") var exitUri: String? = null,
    @SerializedName("authorisation_url") var authorisationUrl: String? = null,
    /**
     * If true, the payer will not be able to edit their customer details within the flow.
     * If the customer details are collected as part of bank_authorisation then GC will set this value to true mid flow.
     * You can only lock customer details if these have already been completed as a part of the billing request.
     */
    @SerializedName("lock_customer_details") var lockCustomerDetails: Boolean? = null,
    /**
     * If true, the payer will not be able to change their bank account within the flow.
     * If the bank_account details are collected as part of bank_authorisation then GC will set this value to true mid flow.
     * You can only lock bank account if these have already been completed as a part of the billing request.
     */
    @SerializedName("lock_bank_account") var lockBankAccount: Boolean? = null,
    @SerializedName("session_token") var sessionToken: String? = null,
    @SerializedName("expires_at") var expiresAt: Date? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("links") var links: Links? = null,
    @SerializedName("config") var config: Config? = null,
    @SerializedName("redirect_flow_id") var redirectFlowId: String? = null,
    /**
     * If true, the payer will be able to see redirect action buttons on Thank You page.
     * These action buttons will provide a way to connect back to the billing request flow app if opened within a mobile app.
     * For successful flow, the button will take the payer back the billing request flow where they will see the success screen.
     * For failure, button will take the payer to url being provided against exit_uri field.
     */
    @SerializedName("show_redirect_buttons") var showRedirectButtons: Boolean? = null,
    /**
     * If true, the payer will not be able to change their currency/scheme manually within the flow.
     * Note that this only applies to the mandate only flows - currency/scheme can never be
     * changed when there is a specified subscription or payment.
     */
    @SerializedName("lock_currency") var lockCurrency: Boolean? = null,
    @SerializedName("prefilled_customer") var prefilledCustomer: Customer? = null,
    @SerializedName("prefilled_bank_account") var prefilledBankAccount: CollectBankAccount? = null,
    /**
     * Sets the default language of the Billing Request Flow and the customer. ISO 639-1 code.
     */
    @SerializedName("language") var language: String? = null,
    /**
     * If true, the payer will be able to see a redirect action button on the Success page.
     * This action button will provide a way to redirect the payer to the given redirect_uri.
     * This functionality is helpful when merchants do not want payers to be automatically
     * redirected or on Android devices, where automatic redirections are not possible.
     */
    @SerializedName("show_success_redirect_button") var showSuccessRedirectButton: Boolean? = null,
    /**
     * Identifies whether a Billing Request belongs to a specific customer
     */
    @SerializedName("customer_details_captured") var customerDetailsCaptured: Boolean? = null,
    @SerializedName("redirect_origin") var redirectOrigin: String? = null
)