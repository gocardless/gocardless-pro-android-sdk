package com.gocardless.gocardlesssdk.error

class MalformedResponseException(error: Error?) : GoCardlessException(error) {
}