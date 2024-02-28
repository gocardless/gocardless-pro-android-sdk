package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a collect customer detail resource returned from the API.
 *
 * Additional parameters to help complete the collect_customer_details action
 */
data class CollectCustomerDetails(
    /**
     * Default customer country code, as determined by scheme and payer location
     */
    @SerializedName("default_country_code") var defaultCountryCode: String? = null,
    @SerializedName("incomplete_fields") var incompleteFields: CustomerDetailsIncompleteFields? = null,
)

data class CustomerDetailsIncompleteFields(
    /**
     * A customer field that needs to be collected
     */
    @SerializedName("customer") var customer: ArrayList<String> = arrayListOf(),
    /**
     * A customer_billing_detail field that needs to be collected
     */
    @SerializedName("customer_billing_detail") var customerBillingDetail: ArrayList<String> = arrayListOf(),
)
