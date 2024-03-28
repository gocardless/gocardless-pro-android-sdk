package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Payment objects represent payments from a customer to a creditor,
 */
data class Payment(
    /**
     * Unique identifier, beginning with “PM”.
     */
    @SerializedName("id") var id: String? = null,
    /**
     * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    @SerializedName("amount") var amount: Int? = null,
    /**
     * Amount refunded, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    @SerializedName("amount_refunded") var amountRefunded: Int? = null,
    /**
     * A future date on which the payment should be collected.
     * If not specified, the payment will be collected as soon as possible.
     * If the value is before the mandate’s next_possible_charge_date creation will fail.
     * If the value is not a working day it will be rolled forwards to the next available one.
     */
    @SerializedName("charge_date") var chargeDate: Date? = null,
    /**
     * Fixed timestamp, recording when this resource was created.
     */
    @SerializedName("created_at") var createdAt: Date? = null,
    /**
     * ISO 4217 currency code. Currently “AUD”, “CAD”, “DKK”, “EUR”, “GBP”, “NZD”, “SEK” and “USD” are supported.
     */
    @SerializedName("currency") var currency: String? = null,
    /**
     * A human-readable description of the payment.
     */
    @SerializedName("description") var description: String? = null,
    /**
     * This field indicates whether the ACH payment is processed through Faster ACH or standard ACH.
     */
    @SerializedName("faster_ach") var fasterAch: Boolean? = null,
    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and values up to 500 characters.
     */
    @SerializedName("metadata") var metadata: MetaData? = null,
    /**
     * An optional reference that will appear on your customer’s bank statement.
     * The character limit for this reference is dependent on the scheme.
     */
    @SerializedName("reference") var reference: String? = null,
    /**
     * On failure, automatically retry the payment using intelligent retries. Default is false.
     */
    @SerializedName("retry_if_possible") var retryIfPossible: Boolean? = null,
    /**
     * On failure, automatically retry the payment using intelligent retries. Default is false.
     */
    @SerializedName("status") var status: PaymentStatus? = null,
    /**
     * Payment links
     */
    @SerializedName("links") var links: PaymentLinks? = null,
    /**
     * Payment FX
     */
    @SerializedName("fx") var fx: PaymentFx? = null,
)

/**
 * Foreign Exchange
 */
data class PaymentFx(
    /**
     * Estimated rate that will be used in the foreign exchange of the amount into the fx_currency.
     */
    @SerializedName("estimated_exchange_rate") var estimatedExchangeRate: String? = null,
    /**
     * Rate used in the foreign exchange of the amount into the fx_currency.
     */
    @SerializedName("exchange_rate") var exchangeRate: String? = null,
    /**
     * Amount that was paid out in the fx_currency after foreign exchange.
     * Present only after the resource has been paid out.
     */
    @SerializedName("fx_amount") var fxAmount: Int? = null,
    /**
     * ISO 4217 code for the currency in which amounts will be paid out (after foreign exchange).
     */
    @SerializedName("fx_currency") var fxCurrency: String? = null,
)

/**
 * Status of payment object
 */
enum class PaymentStatus {
    @SerializedName("pending_customer_approval")
    PendingCustomerApproval,

    @SerializedName("pending_submission")
    PendingSubmission,

    @SerializedName("submitted")
    Submitted,

    @SerializedName("confirmed")
    Confirmed,

    @SerializedName("paid_out")
    PaidOut,

    @SerializedName("cancelled")
    Cancelled,

    @SerializedName("customer_approval_denied")
    CustomerApprovalDenied,

    @SerializedName("failed")
    Failed,

    @SerializedName("charged_back")
    ChargedBack,
}

/**
 * Links of a payment object
 */
data class PaymentLinks(
    /**
     * ID of creditor to which the collected payment will be sent.
     */
    @SerializedName("creditor") var creditor: String? = null,
    /**
     * ID of instalment_schedule from which this payment was created.
     */
    @SerializedName("instalment_schedule") var instalmentSchedule: String? = null,
    /**
     * ID of the mandate against which this payment should be collected.
     */
    @SerializedName("mandate") var mandate: String? = null,
    /**
     * ID of payout which contains the funds from this payment.
     */
    @SerializedName("payout") var payout: String? = null,
    /**
     * ID of subscription from which this payment was created.
     */
    @SerializedName("subscription") var subscription: String? = null,
)