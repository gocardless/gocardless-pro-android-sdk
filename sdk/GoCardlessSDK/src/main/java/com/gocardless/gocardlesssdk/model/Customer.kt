package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Customer objects hold the contact details for a customer.
 * A customer can have several customer bank accounts, which in turn can have several Direct Debit mandates.
 */
data class Customer(
    /**
     * Unique identifier, beginning with “CU”.
     */
    @SerializedName("id") var id: String? = null,
    /**
     * The first line of the customer’s address.
     */
    @SerializedName("address_line1") var addressLine1: String? = null,
    /**
     * The second line of the customer’s address.
     */
    @SerializedName("address_line2") var addressLine2: String? = null,
    /**
     * The third line of the customer’s address.
     */
    @SerializedName("address_line3") var addressLine3: String? = null,
    /**
     * The city of the customer’s address.
     */
    @SerializedName("city") var city: String? = null,
    /**
     * Customer’s company name.
     * Required unless a given_name and family_name are provided.
     * For Canadian customers, the use of a company_name value will mean that any
     * mandate created from this customer will be considered
     * to be a “Business PAD” (otherwise, any mandate will be considered to be a “Personal PAD”).
     */
    @SerializedName("company_name") var companyName: String? = null,
    /**
     * ISO 3166-1 alpha-2 code.
     */
    @SerializedName("country_code") var countryCode: String? = null,
    /**
     * Fixed timestamp, recording when this resource was created.
     */
    @SerializedName("created_at") var createdAt: Date? = null,
    /**
     * Customer’s email address. Required in most cases,
     * as this allows GoCardless to send notifications to this customer.
     */
    @SerializedName("email") var email: String? = null,
    /**
     * Customer’s surname. Required unless a company_name is provided.
     */
    @SerializedName("family_name") var familyName: String? = null,
    /**
     * Customer’s first name. Required unless a company_name is provided.
     */
    @SerializedName("given_name") var givenName: String? = null,
    /**
     * ISO 639-1 code. Used as the language for notification emails sent by GoCardless
     * if your organisation does not send its own
     */
    @SerializedName("language") var language: String? = null,
    /**
     * Key-value store of custom data. Up to 3 keys are permitted,
     * with key names up to 50 characters and values up to 500 characters.
     */
    @SerializedName("metadata") var metadata: MetaData? = MetaData(),
    /**
     * ITU E.123 formatted phone number, including country code.
     */
    @SerializedName("phone_number") var phoneNumber: String? = null,
    /**
     * The customer’s postal code.
     */
    @SerializedName("postal_code") var postalCode: String? = null,
    /**
     * The customer’s address region, county or department.
     * For US customers a 2 letter ISO3166-2:US state code is required (e.g. CA for California).
     */
    @SerializedName("region") var region: String? = null,
    /**
     * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or organisationsnummer) of the customer.
     * Must be supplied if the customer’s bank account is denominated in Swedish krona (SEK).
     * This field cannot be changed once it has been set.
     */
    @SerializedName("swedish_identity_number") var swedishIdentityNumber: String? = null,
)