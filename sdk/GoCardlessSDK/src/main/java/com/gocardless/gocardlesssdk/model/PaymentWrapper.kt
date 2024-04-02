package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Wrapper class for payment requests.
 *
 * @property payments The payment requests associated with this wrapper.
 */
data class PaymentWrapper(
    @SerializedName("payments") var payments: Payment? = Payment()
)