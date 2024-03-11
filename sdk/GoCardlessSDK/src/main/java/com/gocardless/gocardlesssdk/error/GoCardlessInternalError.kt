package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when an internal error occurred while processing your request.
 */
class GoCardlessInternalError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)