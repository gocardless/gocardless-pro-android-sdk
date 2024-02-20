package com.gocardless.gocardlesssdk

import com.gocardless.gocardlesssdk.model.Environment
import com.gocardless.gocardlesssdk.network.GoCardlessAPI
import com.gocardless.gocardlesssdk.network.HeaderInterceptor
import com.gocardless.gocardlesssdk.service.CustomerService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoCardlessSDK {
    private var initialised: Boolean = false

    lateinit var customerService: CustomerService

    fun initSDK(accessToken: String, environment: Environment) {
        if (initialised) {
            return
        }
        registerDependencies(accessToken, environment)
        initialised = true
    }

    private fun registerDependencies(accessToken: String, environment: Environment) {
        val headerInterceptor = HeaderInterceptor(accessToken)

        val client = OkHttpClient
            .Builder()
            .addInterceptor(headerInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(environment.baseUrl)
            .client(client)
            .build()

        val goCardlessAPI = retrofit.create(GoCardlessAPI::class.java)

        customerService = CustomerService(goCardlessAPI)
    }
}