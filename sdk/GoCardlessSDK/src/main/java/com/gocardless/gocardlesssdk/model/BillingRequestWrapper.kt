package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Wrapper class for billing requests.
 *
 * @property billingRequests The billing requests associated with this wrapper.
 */
data class BillingRequestWrapper(
    @SerializedName("billing_requests") var billingRequests: BillingRequest? = BillingRequest()
)

/**
 * Wrapper class for list of billing requests.
 *
 * @property billingRequests The billing requests associated with this wrapper.
 */
data class BillingRequestList(
    @SerializedName("billing_requests") var billingRequests: List<BillingRequest>? = emptyList(),
    @SerializedName("meta") var meta: CursorPagination? = null,
)
