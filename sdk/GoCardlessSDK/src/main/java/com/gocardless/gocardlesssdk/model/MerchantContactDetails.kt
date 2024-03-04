package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class MerchantContactDetails(
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("name") var name: String? = null
)