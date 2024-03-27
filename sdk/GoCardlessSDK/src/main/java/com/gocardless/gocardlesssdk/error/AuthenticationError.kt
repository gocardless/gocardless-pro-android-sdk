package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when user credentials/api key provided is invalid.
 */
class AuthenticationError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)