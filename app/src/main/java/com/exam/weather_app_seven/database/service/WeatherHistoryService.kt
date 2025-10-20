package com.exam.weather_app_seven.database.service

import com.exam.weather_app_seven.database.dao.WeatherHistoryDao
import com.exam.weather_app_seven.database.entity.WeatherHistoryEntity
import javax.inject.Inject

class WeatherHistoryService @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao
) {
    suspend fun insertWeatherHistory(weatherHistoryEntity: WeatherHistoryEntity) {
        weatherHistoryDao.insertWeatherHistory(weatherHistoryEntity)
    }

    suspend fun getWeatherListUserById(userId: String) =
        weatherHistoryDao.getWeatherListByUserId(userId = userId)
}