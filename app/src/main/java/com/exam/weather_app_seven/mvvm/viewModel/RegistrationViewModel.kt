package com.exam.weather_app_seven.mvvm.viewModel

import androidx.lifecycle.ViewModel
import com.exam.weather_app_seven.mvvm.model.Registration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _dialogMessage = MutableStateFlow("")
    val dialogMessage: StateFlow<String> = _dialogMessage.asStateFlow()

    private val _dialogStatus = MutableStateFlow(false)
    val dialogStatus: StateFlow<Boolean> = _dialogStatus.asStateFlow()

    fun setUserName(value: String) {
        _userName.value = value
    }

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    fun validateAndRegister(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        when {
            _userName.value.isBlank() -> onFailure("Username cannot be empty")
            _email.value.isBlank() -> onFailure("Email cannot be empty")
            _password.value.isBlank() -> onFailure("Password cannot be empty")
            _confirmPassword.value.isBlank() -> onFailure("Confirm password cannot be empty")
            _password.value != _confirmPassword.value -> onFailure("Passwords do not match")
            else -> {
                onSuccess()
            }
        }
    }

    fun showDialog(show: Boolean) {
        _showDialog.value = show
    }

    fun setDialogMessage(message: String) {
        _dialogMessage.value = message
    }

    fun setDialogStatus(status: Boolean) {
        _dialogStatus.value = status
    }

    fun clearForm() {
        _userName.value = ""
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _showDialog.value = false
        _dialogMessage.value = ""
        _dialogStatus.value = false
    }
}
