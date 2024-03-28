package com.gocardless.gocardlesssdk.model

import com.google.gson.annotations.SerializedName

/**
 * All list/index endpoints are ordered and paginated reverse chronologically by default.
 */
data class CursorPagination(
    @SerializedName("cursors")
    val cursors: Cursors? = null,
    /** Upper bound for the number of objects to be returned. Defaults to 50. Maximum of 500. */
    @SerializedName("limit")
    val limit: Int? = null,
)

data class Cursors(
    /** ID of the object immediately following the array of objects to be returned. */
    @SerializedName("before")
    var before: String? = null,
    /** ID of the object immediately preceding the array of objects to be returned. */
    @SerializedName("after")
    var after: String? = null
)