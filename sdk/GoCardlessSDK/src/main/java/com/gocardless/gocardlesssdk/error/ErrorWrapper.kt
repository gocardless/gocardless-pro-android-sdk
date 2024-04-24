package com.gocardless.gocardlesssdk.error

import com.google.gson.annotations.SerializedName

internal data class ErrorWrapper(
    @SerializedName("error") var errorDetail: ErrorDetail? = ErrorDetail()
)

data class ErrorDetail(
    @SerializedName("message") var message: String? = null,
    @SerializedName("errors") var errors: ArrayList<ErrorReason> = arrayListOf(),
    @SerializedName("documentation_url") var documentationUrl: String? = null,
    @SerializedName("type") var type: ErrorType? = null,
    @SerializedName("request_id") var requestId: String? = null,
    @SerializedName("code") var code: Int? = null
)

data class ErrorReason(
    @SerializedName("reason") var reason: String? = null,
    @SerializedName("field") var field: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("request_pointer") var requestPointer: String? = null
)