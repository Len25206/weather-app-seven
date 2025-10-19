package com.exam.weather_app_seven.database.service

import com.exam.weather_app_seven.database.dao.UserDao
import com.exam.weather_app_seven.database.entity.UserEntity
import javax.inject.Inject

class UserService @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    suspend fun getAllUser(email: String, password: String) =
        userDao.getUserByEmailAndPassword(email, password)

}