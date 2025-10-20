package com.exam.weather_app_seven.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "api_key")
    val apiKey: String
)