package com.gocardless.gocardlesssdk.error

import com.google.gson.annotations.SerializedName

internal data class ErrorWrapper(
    @SerializedName("error") var error: Error? = Error()
)

data class Error(
    @SerializedName("message") var message: String? = null,
    @SerializedName("errors") var errors: ArrayList<Errors> = arrayListOf(),
    @SerializedName("documentation_url") var documentationUrl: String? = null,
    @SerializedName("type") var type: ErrorType? = null,
    @SerializedName("request_id") var requestId: String? = null,
    @SerializedName("code") var code: Int? = null
)

data class Errors(
    @SerializedName("reason") var reason: String? = null,
    @SerializedName("message") var message: String? = null
)