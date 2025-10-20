package com.exam.weather_app_seven.mvvm.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.exam.weather_app_seven.mvvm.model.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor() {
    private val weatherList = mutableListOf<Weather>()
    private val _currentWeather = MutableStateFlow<Weather?>(null)
    val currentWeather: StateFlow<Weather?> = _currentWeather.asStateFlow()
    fun setWeather(weather: Weather) {
        _currentWeather.value = weather
    }

    fun getWeather(): StateFlow<Weather?> {
        return currentWeather
    }
}