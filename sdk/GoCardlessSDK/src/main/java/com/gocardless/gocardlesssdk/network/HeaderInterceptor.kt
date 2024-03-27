package com.gocardless.gocardlesssdk.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for adding custom headers to API requests.
 *
 * This interceptor adds the following headers:
 * - "GoCardless-Version": Specifies the GoCardless API version (e.g., "2015-07-06").
 * - "Authorization": Includes the access token in the format "Bearer {accessToken}".
 *
 * @param accessToken the access token to be included in the "Authorization" header.
 */
internal class HeaderInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("GoCardless-Version", "2015-07-06")
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        )
    }
}