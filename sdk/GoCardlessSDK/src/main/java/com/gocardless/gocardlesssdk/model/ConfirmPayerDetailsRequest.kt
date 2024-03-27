package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class ConfirmPayerDetailsRequest(
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and values up to 500 characters.
     */
    @SerializedName("metadata")
    var metadata: MetaData? = null,

    /**
     * This attribute can be set to true if the payer has indicated that multiple signatures are required for the mandate.
     */
    @SerializedName("payer_requested_dual_signature")
    var payerRequestedDualSignature: Boolean? = null,
)