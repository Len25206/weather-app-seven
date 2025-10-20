package com.exam.weather_app_seven.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.exam.weather_app_seven.database.entity.WeatherHistoryEntity

@Dao
interface WeatherHistoryDao {
    @Insert
    suspend fun insertWeatherHistory(weatherHistoryEntity: WeatherHistoryEntity)

    @Query("select * from weather_history where user_id = :userId")
    suspend fun getWeatherListByUserId(userId: String): List<WeatherHistoryEntity>
}