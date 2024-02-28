package com.gocardless.gocardlesssdk.network

import com.gocardless.gocardlesssdk.error.GoCardlessException

/**
 * Represents the result of an API operation.
 *
 * This sealed class has two subclasses:
 * - [ApiSuccess] for successful results containing a [value].
 * - [ApiError] for error results containing an optional [exception].
 *
 * @param T the type of data associated with the result.
 */
sealed class ApiResult<T>

/**
 * Represents a successful API result.
 *
 * @param value the value associated with the successful result.
 * @param T the type of data contained in the result.
 */
data class ApiSuccess<T>(val value: T) : ApiResult<T>()

/**
 * Represents an error in the API result.
 *
 * @param exception the exception associated with the error (if any).
 * @param T the type of data contained in the result.
 */
data class ApiError<T>(val exception: GoCardlessException?) : ApiResult<T>()