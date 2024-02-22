package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class BillingRequest(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: LocalDateTime?
)
