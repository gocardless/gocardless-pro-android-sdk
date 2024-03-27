package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Notifies the customer linked to the billing request, asking them to authorise it
 */
data class NotifyActionRequest(
    /**
     * Currently, can only be email.
     */
    @SerializedName("notification_type")
    var notificationType: NotificationType,
    /**
     * URL that the payer can be redirected to after authorising the payment.
     */
    @SerializedName("redirect_uri")
    var redirectUri: String? = null,
)

/**
 * Currently, the customer can only be notified by email.
 */
enum class NotificationType {
    @SerializedName("email")
    Email,
}
