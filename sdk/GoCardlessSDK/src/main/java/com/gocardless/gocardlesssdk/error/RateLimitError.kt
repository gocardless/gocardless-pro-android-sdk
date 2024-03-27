package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when number of api requests are exceeded configured rate limit.
 */
class RateLimitError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)