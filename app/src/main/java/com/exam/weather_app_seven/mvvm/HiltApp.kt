package com.exam.weather_app_seven.mvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}