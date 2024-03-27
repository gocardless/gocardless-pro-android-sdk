package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class CollectBankAccount(
    /**
     * Name of the account holder, as known by the bank. Usually this is the same as the name stored with the linked creditor.
     */
    @SerializedName("account_holder_name")
    var accountHolderName: String? = null,
    /**
     *
     * Bank account number
     */
    @SerializedName("account_number")
    var accountNumber: String? = null,
    /**
     * Account number suffix (only for bank accounts denominated in NZD)
     */
    @SerializedName("account_number_suffix")
    var accountNumberSuffix: String? = null,
    /**
     * Bank account type. Required for USD-denominated bank accounts. Must not be provided for bank accounts in other currencies.
     */
    @SerializedName("account_type")
    var accountType: String? = null,
    /**
     * Bank code
     */
    @SerializedName("bank_code")
    var bankCode: String? = null,
    /**
     * Branch code
     */
    @SerializedName("branch_code")
    var branchCode: String? = null,
    /**
     * ISO 3166-1 alpha-2 code. Defaults to the country code of the iban if supplied, otherwise is required.
     */
    @SerializedName("country_code")
    var countryCode: String? = null,
    /**
     * ISO 4217 currency code. Currently “AUD”, “CAD”, “DKK”, “EUR”, “GBP”, “NZD”, “SEK” and “USD” are supported.
     */
    @SerializedName("currency")
    var currency: String? = null,
    /**
     * International Bank Account Number.
     */
    @SerializedName("iban")
    var iban: String? = null,
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and values up to 500 characters.
     */
    @SerializedName("metadata")
    var metadata: MetaData? = null,
    /**
     * A unique record such as an email address, mobile number or company number, that can be used to make and accept payments.
     */
    @SerializedName("pay_id")
    var payId: String? = null,
)