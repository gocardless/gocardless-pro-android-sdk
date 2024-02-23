package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CustomerBillingDetail(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("address_line1") var addressLine1: String? = null,
    @SerializedName("address_line2") var addressLine2: String? = null,
    @SerializedName("address_line3") var addressLine3: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("postal_code") var postalCode: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("swedish_identity_number") var swedishIdentityNumber: String? = null,
    @SerializedName("danish_identity_number") var danishIdentityNumber: String? = null
)