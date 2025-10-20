package com.exam.weather_app_seven.application

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exam.weather_app_seven.application.ui.page.Dashboard
import com.exam.weather_app_seven.application.ui.page.Login
import com.exam.weather_app_seven.application.ui.page.Registration
import com.exam.weather_app_seven.application.ui.page.WeatherHistory
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.viewModel.RegistrationViewModel
import com.exam.weather_app_seven.mvvm.viewModel.UserLoginViewModel
import com.exam.weather_app_seven.mvvm.viewModel.UserViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherHistoryViewModel
import com.exam.weather_app_seven.mvvm.viewModel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val registrationViewModel: RegistrationViewModel = hiltViewModel()
    val userLoginViewModel: UserLoginViewModel = hiltViewModel()
    val weatherHistoryViewModel: WeatherHistoryViewModel = hiltViewModel()
    var userData: User? = null


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
                userViewModel,
                userLoginViewModel,
                userData = { user ->
                    userData = user
                }
            )
        }
        composable(Screen.RegisterPage.route)
        {
            Registration(
                navController,
                userViewModel,
                registrationViewModel,
            )
        }
        composable(Screen.DashboardPage.route) {
            Dashboard(
                navController,
                weatherViewModel,
                userData,
                weatherHistoryViewModel
            )
        }

        composable(Screen.HistoryPage.route) {
            WeatherHistory(
                navController,
                weatherHistoryViewModel,
                userData
            )
        }

    }
}