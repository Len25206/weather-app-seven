package com.exam.weather_app_seven.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherHistoryEntity (
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "weeks")
    val weeks: String,
    @ColumnInfo(name = "date_and_time")
    val dateAndTime: String,
    @ColumnInfo(name = "temperature")
    val temperature: String,
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @ColumnInfo(name = "icon_code")
    val codeIcon: String,
    @ColumnInfo(name = "sunrise")
    val sunrise: String,
    @ColumnInfo("sunset")
    val sunset: String,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: String,
    @ColumnInfo(name = "pressure")
    val pressure: String,
    @ColumnInfo(name = "humidity")
    val humidity: String,
    @ColumnInfo(name = "visibility")
    val visibility: String
)