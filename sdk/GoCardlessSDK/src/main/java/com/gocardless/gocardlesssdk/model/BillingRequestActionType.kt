package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

enum class BillingRequestActionType(val value: String) {
    @SerializedName("collect_customer_details")
    CollectCustomerDetails("collect_customer_details"),

    @SerializedName("collect_bank_account")
    CollectBankAccount("collect_bank_account"),

    @SerializedName("select_institution")
    SelectInstitution("select_institution"),

    @SerializedName("confirm_payer_details")
    ConfirmPayerDetails("confirm_payer_details"),

    @SerializedName("fulfil")
    Fulfil("fulfil"),

    @SerializedName("cancel")
    Cancel("cancel"),

    @SerializedName("notify")
    Notify("notify"),
}