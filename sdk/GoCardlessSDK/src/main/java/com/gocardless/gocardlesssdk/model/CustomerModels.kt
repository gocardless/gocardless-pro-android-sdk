package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Customers(
    val customers: List<Customer>,
    val meta: Meta
)

data class Customer(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val email: String,
    @SerializedName("given_name")
    val givenName: String,
    @SerializedName("family_name")
    val familyName: String,
    @SerializedName("company_name")
    val companyName: Any? = null,
    @SerializedName("address_line1")
    val addressLine1: String,
    @SerializedName("address_line2")
    val addressLine2: String? = null,
    @SerializedName("address_line3")
    val addressLine3: String? = null,
    val city: String,
    val region: String? = null,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("country_code")
    val countryCode: String,
    val language: String,
    @SerializedName("swedish_identity_number")
    val swedishIdentityNumber: String? = null,
    @SerializedName("danish_identity_number")
    val danishIdentityNumber: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    val metadata: Metadata
)

data class Metadata(
    @SerializedName("crm_id")
    val crmID: String? = null
)

data class Meta(
    val cursors: Cursors,
    val limit: Long
)

data class Cursors(
    val before: Any? = null,
    val after: Any? = null
)
