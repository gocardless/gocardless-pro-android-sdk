package com.gocardless.gocardlesssdk.error

open class InvalidApiUsageException(error: Error?): GoCardlessException(error) {
}