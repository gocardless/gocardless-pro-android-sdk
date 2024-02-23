package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class Experimentation(
    @SerializedName("is_eligible_for_share_of_wallet_experiments") var isEligibleForShareOfWalletExperiments: Boolean? = null,
    @SerializedName("is_eligible_for_optional_vm_experiments") var isEligibleForOptionalVmExperiments: Boolean? = null,
    @SerializedName("is_eligible_for_institution_experiments") var isEligibleForInstitutionExperiments: Boolean? = null,
    @SerializedName("is_eligible_for_ach_optional_address_experiments") var isEligibleForAchOptionalAddressExperiments: Boolean? = null,
    @SerializedName("is_eligible_for_single_tab_experiments") var isEligibleForSingleTabExperiments: Boolean? = null
)