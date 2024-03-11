package com.gocardless.gocardlesssdk

import com.gocardless.gocardlesssdk.error.ErrorMapper
import com.gocardless.gocardlesssdk.model.Environment
import com.gocardless.gocardlesssdk.network.GoCardlessApi
import com.gocardless.gocardlesssdk.network.HeaderInterceptor
import com.gocardless.gocardlesssdk.service.BillingRequestFlowService
import com.gocardless.gocardlesssdk.service.BillingRequestService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The main entry point for the GoCardless Android SDK.
 *
 * This class initializes the necessary dependencies and prepares the SDK for operation.
 * To use the SDK, create an instance of this class and configure it with your API credentials.
 */
object GoCardlessSDK {
    private var initialised: Boolean = false

    /**
     * Billing Request Service
     */
    lateinit var billingRequestService: BillingRequestService

    /**
     * Billing Request Flow Service
     */
    lateinit var billingRequestFlowService: BillingRequestFlowService

    /**
     * Initializes the GoCardless SDK with the provided access token and environment.
     *
     * This method should be called once during your app's startup to set up the SDK.
     * If the SDK has already been initialized, subsequent calls to this method will be ignored.
     *
     * @param accessToken Your GoCardless API access token.
     * @param environment The environment (sandbox or live) for API requests.
     */
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

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(environment.baseUrl)
            .client(client)
            .build()

        val goCardlessAPI = retrofit.create(GoCardlessApi::class.java)

        val errorMapper = ErrorMapper(gson)

        billingRequestService = BillingRequestService(goCardlessAPI, errorMapper)
        billingRequestFlowService = BillingRequestFlowService(goCardlessAPI, errorMapper)
    }
}