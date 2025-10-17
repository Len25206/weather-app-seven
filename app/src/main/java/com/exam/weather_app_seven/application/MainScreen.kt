package com.exam.weather_app_seven.application

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exam.weather_app_seven.application.ui.Dashboard
import com.exam.weather_app_seven.application.ui.Login
import com.exam.weather_app_seven.application.ui.Registration

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    MainNav.setController(navController)
    BackHandler(enabled =true   ) {
        Log.e("TAG", "MainScreen:")
    }
    NavHost(
        navController = navController,
        startDestination = Screen.LoginPage.route
    ) {
        composable(Screen.LoginPage.route) {
            Login(
                navController,
            )
        }
        composable(Screen.RegisterPage.route)
        {
            Registration(
                navController,
            )
        }
        composable(Screen.DashboardPage.route) {
            Dashboard(
                navController,
            )
        }

    }
}