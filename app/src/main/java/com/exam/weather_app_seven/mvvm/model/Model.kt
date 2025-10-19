package com.exam.weather_app_seven.mvvm.model

import java.util.UUID

data class Weather(
    val lon: Double? = 0.0,
    val lat: Double? = 0.0,
    val weatherMain: String? = "",
    val weatherDescription: String? = "",
    val mainTemp: Double? = 0.0,
    val mainFeelsLike: Double? = 0.0,
    val mainTempMin: Double? = 0.0,
    val mainTempMax: Double? = 0.0,
    val windSpeed: Double? = 0.0,
    val cloudsAll: Int? = 0,
    val visibility: Int? = 0,
    val locationName: String? = "",
    val sysCountry: String? = "",
    val sysSunrise: String? = "",
    val sysSunset: String? = "",
    val pressure: Int? = 0,
    val humidity: Int? = 0,
    val iconCode: String? = ""
)

data class User(
    val id: String? = UUID.randomUUID().toString(),
    val userName: String? = "",
    val password: String? = "",
    val email: String? = ""
)

data class Registration(
    var showDialog: Boolean? = false,
    var dialogStatus: Boolean? = false,
    var messageDialog: String? = "",
    var userName: String? = "",
    var email: String? = "",
    var password: String? = "",
    var confirmPassword: String? = ""
)

data class UserLogin(
    var showDialog: Boolean? = false,
    var dialogStatus: Boolean? = false,
    var messageDialog: String? = "",
    var email: String? = "",
    var password: String? = "",
)


