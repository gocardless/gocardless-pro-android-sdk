package com.gunhansancar.mvvmapp.app

import android.app.Application
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.model.Environment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        GoCardlessSDK.initSDK(
            "",
            Environment.Sandbox
        )
    }
}