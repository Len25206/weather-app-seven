package com.exam.weather_app_seven.mvvm.repository

import com.exam.weather_app_seven.database.entity.WeatherHistoryEntity
import com.exam.weather_app_seven.database.service.WeatherHistoryService
import com.exam.weather_app_seven.mvvm.model.WeatherHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherHistoryRepository @Inject constructor(
    private val weatherHistoryService: WeatherHistoryService
) {
    private val _weatherHistory = MutableStateFlow<List<WeatherHistoryEntity>>(emptyList())
    val weatherHistory: StateFlow<List<WeatherHistoryEntity>> = _weatherHistory.asStateFlow()

    suspend fun setWeatherHistory(userId: String) {
        _weatherHistory.value = weatherHistoryService.getWeatherListUserById(userId)
    }


    suspend fun insertWeatherHistory(weatherHistory: WeatherHistory) {
        weatherHistoryService.insertWeatherHistory(
            WeatherHistoryEntity(
                id = weatherHistory.id ?: "",
                userId = weatherHistory.userId ?: "",
                location = weatherHistory.location ?: "",
                dateAndTime = weatherHistory.dateAndTime ?: 0,
                weatherDescription = weatherHistory.weatherDescription ?: "",
                iconCode = weatherHistory.iconCode ?: ""
            )
        )
    }


}