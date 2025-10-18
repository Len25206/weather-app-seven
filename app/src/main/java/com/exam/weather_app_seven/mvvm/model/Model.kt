package com.exam.weather_app_seven.mvvm.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

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

@Serializable
data class User(
    @SerializedName("user_name")
    val userName: String? = "",
    @SerializedName("email")
    val userEmail: String? = "",
    @SerializedName("password")
    val userPassword: String? = ""
)


