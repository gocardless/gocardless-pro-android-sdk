package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when the action you are trying to perform is invalid
 * due to the state of the resource you are requesting it on.
 */
open class InvalidStateError(errorDetail: ErrorDetail?) : GoCardlessError(errorDetail)