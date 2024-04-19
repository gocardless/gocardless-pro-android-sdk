package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

data class MandateConstraints(
    /**
     * List of periodic limits and constraints which apply to them
     */
    @SerializedName("periodic_limits") var periodicLimits: List<PeriodicLimit>? = null,

    /**
     * The date from which payments can be taken. YYYY-MM-DD
     * This is an optional field and if it is not supplied the start date will be set to the day authorisation happens.
     */
    @SerializedName("start_date") var startDate: String? = null,

    /**
     * The latest date at which payments can be taken, must occur after start_date if present YYYY-MM-DD
     * This is an optional field and if it is not supplied the agreement will be considered open and will not have an end date. Keep in mind the end date must take into account how long it will take the user to set up this agreement via the Billing Request.
     */
    @SerializedName("end_date") var endDate: String? = null,

    /**
     * The maximum amount that can be charged for a single payment. Required for VRP.
     */
    @SerializedName("max_amount_per_payment") var maxAmountPerPayment: Int? = null,
)

data class PeriodicLimit(
    /**
     * The repeating period for this mandate
     */
    @SerializedName("period") var period: Period? = null,

    /**
     * The maximum total amount that can be charged for all payments in this periodic limit. Required for VRP.
     */
    @SerializedName("max_total_amount") var maxTotalAmount: Int? = null,

    /**
     * (Optional) The maximum number of payments that can be collected in this periodic limit.
     */
    @SerializedName("max_payments") var maxPayments: Int? = null,

    /**
     * The alignment of the period.
     */
    @SerializedName("alignment") var alignment: Alignment? = null,

    )

enum class Period {
    @SerializedName("day")
    DAY,

    @SerializedName("week")
    WEEK,

    @SerializedName("month")
    MONTH,

    @SerializedName("year")
    YEAR,

    @SerializedName("flexible")
    FLEXIBLE,
}

enum class Alignment {
    /**
     * This will finish on the end of the current period. For example this will expire on the Monday for the current week or the January for the next year.
     */
    @SerializedName("calendar")
    CALENDAR,

    /**
     *  This will finish on the next instance of the current period. For example Monthly it will expire on the same day of the next month, or yearly the same day of the next year.
     */
    @SerializedName("creation_date")
    CREATION_DATE
}