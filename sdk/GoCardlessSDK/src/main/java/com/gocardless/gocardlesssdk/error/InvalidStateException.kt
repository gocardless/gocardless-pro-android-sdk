package com.gocardless.gocardlesssdk.error

open class InvalidStateException(error: Error?): GoCardlessException(error) {
}