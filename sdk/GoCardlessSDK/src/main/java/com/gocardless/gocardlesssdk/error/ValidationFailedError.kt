package com.gocardless.gocardlesssdk.error

/**
 * Represents an error that occurs when the parameters submitted with your request were invalid.
 */
open class ValidationFailedError(errorDetail: ErrorDetail?): GoCardlessError(errorDetail) {
}