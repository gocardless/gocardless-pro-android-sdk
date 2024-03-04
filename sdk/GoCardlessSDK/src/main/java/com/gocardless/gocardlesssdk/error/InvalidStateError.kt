package com.gocardless.gocardlesssdk.error

open class InvalidStateError(errorDetail: ErrorDetail?): GoCardlessError(errorDetail) {
}