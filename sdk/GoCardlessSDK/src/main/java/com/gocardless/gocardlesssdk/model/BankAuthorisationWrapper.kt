package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * Wrapper class for bank authorisations
 *
 * @property bankAuthorisation The bank authorisations associated with this wrapper.
 */
data class BankAuthorisationWrapper(
    @SerializedName("bank_authorisations") var bankAuthorisations: BankAuthorisation? = null
)