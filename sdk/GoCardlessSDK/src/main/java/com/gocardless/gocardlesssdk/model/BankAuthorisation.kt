package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Represents a bank authorisation resource returned from the API.
 *
 * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created against
 * a specific bank, usually the bank that provides the payer's account.
 *
 * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing Request
 * Flows) to ensure we meet regulatory requirements for checkout flows.
 */
data class BankAuthorisation(
    /**
     * Type of authorisation, can be either 'mandate' or 'payment'.
     */
    @SerializedName("authorisation_type") var authorisationType: AuthorisationType? = null,
    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when the user has been authorised.
     */
    @SerializedName("authorised_at") var authorisedAt: Date? = null,
    /**
     * Timestamp when the flow was created
     */
    @SerializedName("created_at") var createdAt: Date? = null,
    /**
     * Timestamp when the url will expire. Each authorisation url currently lasts for 15 minutes,
     * but this can vary by bank.
     */
    @SerializedName("expires_at") var expiresAt: Date? = null,
    /**
     * Unique identifier, beginning with "BAU".
     */
    @SerializedName("id") var id: String? = null,
    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when the authorisation URL has
     * been visited.
     */
    @SerializedName("last_visited_at") var lastVisitedAt: Date? = null,
    @SerializedName("links") var links: Links? = null,
    /**
     * URL to a QR code PNG image of the bank authorisation url. This QR code can be used as an
     * alternative to providing the `url` to the payer to allow them to authorise with their mobile
     * devices.
     */
    @SerializedName("qr_code_url") var qrCodeUrl: String? = null,
    /**
     * URL that the payer can be redirected to after authorising the payment.
     *
     * On completion of bank authorisation, the query parameter of either `outcome=success` or
     * `outcome=failure` will be appended to the `redirect_uri` to indicate the result of the bank
     * authorisation. If the bank authorisation is expired, the query parameter `outcome=timeout`
     * will be appended to the `redirect_uri`, in which case you should prompt the user to try the
     * bank authorisation step again.
     *
     * The `redirect_uri` you provide should handle the `outcome` query parameter for displaying the
     * result of the bank authorisation as outlined above.
     *
     * The BillingRequestFlow ID will also be appended to the `redirect_uri` as query parameter
     * `id=BRF123`.
     *
     * Defaults to `https://pay.gocardless.com/billing/static/thankyou`.
     */
    @SerializedName("redirect_uri") var redirectUri: String? = null,
    /**
     * URL for an oauth flow that will allow the user to authorise the payment
     */
    @SerializedName("url") var url: String? = null,
)

/**
 * Type of authorisation, can be either 'mandate' or 'payment'.
 */
enum class AuthorisationType {
    @SerializedName("mandate")
    MANDATE,

    @SerializedName("payment")
    PAYMENT,

    @SerializedName("unknown")
    UNKNOWN
}
