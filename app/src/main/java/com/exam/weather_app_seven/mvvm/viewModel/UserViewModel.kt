package com.exam.weather_app_seven.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val user = userRepository.user
    fun insertUser(user: User?) {
        viewModelScope.launch {
            if (user != null) {
                userRepository.insertUser(user)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.getUserByEmailAndPassword(email, password)
        }
    }

    fun resetUser() {
        viewModelScope.launch {
            userRepository.resetUser()
        }
    }
}