package com.gocardless.gocardlesssdk.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestNetworkManager {
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    private val client = OkHttpClient
        .Builder()
        .build()

    fun <T> create(baseUrl: HttpUrl, apiClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(client)
            .build()

        return retrofit.create(apiClass)
    }
}