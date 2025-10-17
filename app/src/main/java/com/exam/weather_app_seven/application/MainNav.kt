package com.exam.weather_app_seven.application

import android.annotation.SuppressLint
import androidx.navigation.NavController

object MainNav {
    @SuppressLint("StaticFieldLeak")
    var navController: NavController? = null


    fun setController(
        controller: NavController
    ) {
        navController = controller
    }

    fun goTo(route:String){
        navController?.navigate(route)
    }
}