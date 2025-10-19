package com.exam.weather_app_seven.application

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exam.weather_app_seven.application.ui.Dashboard
import com.exam.weather_app_seven.application.ui.Login
import com.exam.weather_app_seven.application.ui.Registration
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    MainNav.setController(navController)
    BackHandler(enabled = true) {
        Log.e("TAG", "MainScreen:")
    }
    NavHost(
        navController = navController,
        startDestination = Screen.LoginPage.route
    ) {
        composable(Screen.LoginPage.route) {
            Login(
                navController,
                userViewModel
            )
        }
        composable(Screen.RegisterPage.route)
        {
            Registration(
                navController,
                userViewModel
            )
        }
        composable(Screen.DashboardPage.route) {
            Dashboard(
                navController,
                weatherViewModel
            )
        }

    }
}