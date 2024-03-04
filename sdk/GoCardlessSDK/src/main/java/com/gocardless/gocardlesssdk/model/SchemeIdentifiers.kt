package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class SchemeIdentifiers(
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("advance_notice") var advanceNotice: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("reference") var reference: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("bank_statement_name") var bankStatementName: String? = null,
    @SerializedName("registered_name") var registeredName: String? = null
)