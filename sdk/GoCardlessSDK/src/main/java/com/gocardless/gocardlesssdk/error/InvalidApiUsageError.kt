package com.gocardless.gocardlesssdk.error

open class InvalidApiUsageError(errorDetail: ErrorDetail?): GoCardlessError(errorDetail) {
}