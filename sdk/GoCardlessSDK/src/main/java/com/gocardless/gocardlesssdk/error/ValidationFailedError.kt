package com.gocardless.gocardlesssdk.error

open class ValidationFailedError(errorDetail: ErrorDetail?): GoCardlessError(errorDetail) {
}