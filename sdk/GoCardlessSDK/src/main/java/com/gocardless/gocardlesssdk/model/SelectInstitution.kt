package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class SelectInstitution(
    /**
     * (required) ISO 3166-1 alpha-2 code. Defaults to the country code of the iban if supplied, otherwise is required.
     */
    @SerializedName("country_code")
    var countryCode: String? = null,

    /**
     * (required) The unique identifier for this institution.
     */
    @SerializedName("institution")
    var institution: String? = null
)