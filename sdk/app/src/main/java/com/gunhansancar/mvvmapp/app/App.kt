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
            "sandbox_ReyBZA9xcVZ-ktSxjfG0Duhzr7h-c9Pjt-UTi8RI",
            Environment.Sandbox
        )
    }
}