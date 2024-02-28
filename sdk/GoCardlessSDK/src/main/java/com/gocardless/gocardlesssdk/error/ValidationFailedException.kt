package com.gocardless.gocardlesssdk.error

open class ValidationFailedException(error: Error?): GoCardlessException(error) {
}