package com.exam.weather_app_seven.mvvm.repository

import com.exam.weather_app_seven.database.entity.UserEntity
import com.exam.weather_app_seven.database.service.UserService
import com.exam.weather_app_seven.mvvm.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService
) {
    private val userList = mutableListOf<User>()
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    suspend fun setUser(user: User?){
        _user.value = user
    }
    suspend fun insertUser(user: User) {
        userService.insertUser(
            userEntity = UserEntity(
                id = user.id ?: "",
                userName = user.userName ?: "",
                password = user.password ?: "",
                email = user.email ?: ""
            )
        )
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String) {
        val userEntity = userService.getAllUser(email, password)
        if (userEntity != null) {
            _user.value = User(
                id = userEntity.id,
                userName = userEntity.userName,
                password = userEntity.password,
                email = userEntity.email
            )
        }
    }

    fun resetUser() {
        _user.value?.userName = ""
        _user.value?.password = ""
        _user.value?.email = ""
        _user.value = null
        userList.clear()
    }
    // Removed getUser() function to avoid JVM signature clash error
}
