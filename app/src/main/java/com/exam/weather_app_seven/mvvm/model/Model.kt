package com.exam.weather_app_seven.mvvm.model

data class Weather(
    val lon: Double,
    val lat: Double,
    val weatherMain: String,
    val weatherDescription: String,
    val mainTemp: Double,
    val mainFeelsLike: Double,
    val mainTempMin: Double,
    val mainTempMax: Double,
    val windSpeed: Double,
    val cloudsAll: Int,
    val visibility: Int,
    val locationName: String,
    val sysCountry: String,
    val sysSunrise: String,
    val sysSunset: String,
    val pressure: Int? = 0,
    val humidity: Int? = 0
)


