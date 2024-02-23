package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Actions(
    @SerializedName("type") var type: String? = null,
    @SerializedName("required") var required: Boolean? = null,
    @SerializedName("completes_actions") var completesActions: ArrayList<String> = arrayListOf(),
    @SerializedName("requires_actions") var requiresActions: ArrayList<String> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("available_currencies") var availableCurrencies: ArrayList<String> = arrayListOf()
)