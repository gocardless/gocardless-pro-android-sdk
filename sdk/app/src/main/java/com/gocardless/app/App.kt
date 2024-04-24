package com.gocardless.app

import android.app.Application
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.network.Environment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        GoCardlessSDK.initSDK(
            "",
            environment
        )
    }

    companion object {
        val environment = Environment.Sandbox
    }
}