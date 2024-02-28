package com.gocardless.gocardlesssdk.error

import com.google.gson.Gson
import java.io.Reader

/**
 * Map the received errors to the correct `Error` objects
 */
class ErrorMapper(private val gson: Gson) {
    /**
     * Maps an error response to an exception.
     * @param code HTTP status code.
     * @param reader Char stream of the response body
     */
    fun process(code: Int, reader: Reader?): GoCardlessException {
        try {
            val body = gson.fromJson(reader, ErrorWrapper::class.java)
            val error = body?.error

            when (code) {
                401 -> return AuthenticationException(error)
                403 -> return PermissionException(error)
                429 -> return RateLimitException(error)
            }

            when (error?.type) {
                ErrorType.GOCARDLESS -> return GoCardlessInternalException(error)
                ErrorType.INVALID_API_USAGE -> return InvalidApiUsageException(error)
                ErrorType.INVALID_STATE -> return InvalidStateException(error)
                ErrorType.VALIDATION_FAILED -> return ValidationFailedException(error)
                else -> {}
            }

            return GoCardlessException(body?.error)
        } catch (exception: Exception) {
            return MalformedResponseException(Error(exception.message))
        }
    }
}