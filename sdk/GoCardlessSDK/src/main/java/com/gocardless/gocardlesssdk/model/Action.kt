package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * List of actions that can be performed before this billing request can be fulfilled.
 */
data class Action(
    /**
     * Unique identifier for the action.
     */
    @SerializedName("type") var type: ActionType? = null,
    /**
     * Informs you whether the action is required to fulfil the billing request or not.
     */
    @SerializedName("required") var required: Boolean? = null,
    /**
     * Which other action types this action can complete.
     */
    @SerializedName("completes_actions") var completesActions: ArrayList<String> = arrayListOf(),
    /**
     * Requires completing these actions before this action can be completed.
     */
    @SerializedName("requires_actions") var requiresActions: ArrayList<String> = arrayListOf(),
    /**
     * Status of the action
     */
    @SerializedName("status") var status: ActionStatus? = null,
    /**
     * List of currencies the current mandate supports
     */
    @SerializedName("available_currencies") var availableCurrencies: ArrayList<String> = arrayListOf(),
    /**
     * Describes the behaviour of bank authorisations, for the bank_authorisation action
     */
    @SerializedName("bank_authorisation") var bankAuthorisation: BankAuthorisation? = null,
    /**
     * Additional parameters to help complete the collect_customer_details action
     */
    @SerializedName("collect_customer_details") var collectCustomerDetails: CollectCustomerDetails? = null,
)

/**
 * Status of the action
 */
enum class ActionStatus {
    @SerializedName("pending")
    Pending,

    @SerializedName("completed")
    Completed,
}

enum class ActionType {
    @SerializedName("choose_currency")
    CHOOSE_CURRENCY,

    @SerializedName("collect_amount")
    COLLECT_AMOUNT,

    @SerializedName("collect_customer_details")
    COLLECT_CUSTOMER_DETAILS,

    @SerializedName("collect_bank_account")
    COLLECT_BANK_ACCOUNT,

    @SerializedName("bank_authorisation")
    BANK_AUTHORISATION,

    @SerializedName("confirm_payer_details")
    CONFIRM_PAYER_DETAILS,

    @SerializedName("select_institution")
    SELECT_INSTITUTION,

    @SerializedName("unknown")
    UNKNOWN
}