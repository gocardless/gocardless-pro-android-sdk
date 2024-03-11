package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when there is an error with the request you made.
 */
open class InvalidApiUsageError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)