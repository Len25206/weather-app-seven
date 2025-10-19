package com.exam.weather_app_seven.mvvm.viewModel
import androidx.lifecycle.ViewModel
import com.exam.weather_app_seven.mvvm.model.UserLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor() : ViewModel() {

    private val _userLogin = MutableStateFlow(UserLogin())
    val userLogin: StateFlow<UserLogin> = _userLogin.asStateFlow()

    fun setShowDialog(show: Boolean) {
        _userLogin.value = _userLogin.value.copy(showDialog = show)
    }

    fun setDialogStatus(status: Boolean) {
        _userLogin.value = _userLogin.value.copy(dialogStatus = status)
    }

    fun setMessageDialog(message: String) {
        _userLogin.value = _userLogin.value.copy(messageDialog = message)
    }

    fun setEmail(email: String) {
        _userLogin.value = _userLogin.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _userLogin.value = _userLogin.value.copy(password = password)
    }

    // Optional: validation logic for login inputs, with callbacks
    fun validateLogin(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val email = _userLogin.value.email ?: ""
        val password = _userLogin.value.password ?: ""
        when {
            email.isBlank() -> onFailure("Email cannot be empty")
            password.isBlank() -> onFailure("Password cannot be empty")
            else -> onSuccess()
        }
    }

    fun clearLoginForm() {
        _userLogin.value = UserLogin()
    }
}
