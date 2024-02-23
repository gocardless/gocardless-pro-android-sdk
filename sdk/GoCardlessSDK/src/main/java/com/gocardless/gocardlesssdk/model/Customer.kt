package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Customer(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("given_name") var givenName: String? = null,
    @SerializedName("family_name") var familyName: String? = null,
    @SerializedName("company_name") var companyName: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("metadata") var metadata: MetaData? = MetaData()
)