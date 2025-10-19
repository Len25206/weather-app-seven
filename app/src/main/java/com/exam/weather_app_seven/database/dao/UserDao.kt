package com.exam.weather_app_seven.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.exam.weather_app_seven.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE email = :email and password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?


}