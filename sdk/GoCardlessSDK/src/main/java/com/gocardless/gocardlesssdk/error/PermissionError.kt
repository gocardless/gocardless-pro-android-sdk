package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when user is not permitted to do desired action.
 */
class PermissionError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)