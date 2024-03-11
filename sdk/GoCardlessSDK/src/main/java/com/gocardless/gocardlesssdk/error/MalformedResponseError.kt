package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when a response is returned from the API which isn't valid JSON (for example, an
 * HTML error page returned from a load balancer)
 */
class MalformedResponseError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)