package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("merchant_contact_details") var merchantContactDetails: MerchantContactDetails? = MerchantContactDetails(),
    @SerializedName("scheme_identifiers") var schemeIdentifiers: ArrayList<SchemeIdentifiers> = arrayListOf()
)