package com.exam.weather_app_seven.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "dateAndTime")
    val dateAndTime: String,
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @ColumnInfo(name = "icon_code")
    val iconCode: String
)