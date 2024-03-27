package com.gocardless.gocardlesssdk.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK)

    /**
     * Create date based on given string date
     */
    fun create(dateString: String): Date {
        return formatter.parse(dateString) as Date
    }
}