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
    fun process(code: Int, reader: Reader?): GoCardlessError {
        try {
            val body = gson.fromJson(reader, ErrorWrapper::class.java)
            val error = body?.errorDetail

            when (code) {
                401 -> return AuthenticationError(error)
                403 -> return PermissionError(error)
                429 -> return RateLimitError(error)
            }

            when (error?.type) {
                ErrorType.GOCARDLESS -> return GoCardlessInternalError(error)
                ErrorType.INVALID_API_USAGE -> return InvalidApiUsageError(error)
                ErrorType.INVALID_STATE -> return InvalidStateError(error)
                ErrorType.VALIDATION_FAILED -> return ValidationFailedError(error)
                else -> {}
            }

            return GoCardlessError(body?.errorDetail)
        } catch (exception: Exception) {
            return MalformedResponseError(ErrorDetail(exception.message))
        }
    }
}