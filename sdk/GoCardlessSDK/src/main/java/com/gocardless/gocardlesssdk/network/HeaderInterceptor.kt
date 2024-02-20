package com.gocardless.gocardlesssdk.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val accessToken: String) : Interceptor {
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