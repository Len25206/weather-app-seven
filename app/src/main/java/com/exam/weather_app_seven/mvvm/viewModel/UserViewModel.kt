package com.exam.weather_app_seven.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_app_seven.mvvm.model.User
import com.exam.weather_app_seven.mvvm.repository.UserRepository
import com.exam.weather_app_seven.supabase.SupabaseClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response: List<User> = SupabaseClient.client.get("/rest/v1/user") {
                    parameter("select", "*")
                }.body()

                _users.value = response
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                Log.e("UserViewModel", "Error fetching users", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // For insert
    suspend fun insertUser(user: User): Result<User> {
        return try {
            val response: User = SupabaseClient.client.post("/rest/v1/user") {
                contentType(ContentType.Application.Json)
                setBody(user)
                parameter("select", "*")
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // For update
    suspend fun updateUser(id: Int, user: User): Result<User> {
        return try {
            val response: User = SupabaseClient.client.patch("/rest/v1/user?id=eq.$id") {
                contentType(ContentType.Application.Json)
                setBody(user)
                parameter("select", "*")
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // For delete
    suspend fun deleteUser(id: Int): Result<Unit> {
        return try {
            SupabaseClient.client.delete("/rest/v1/user?id=eq.$id")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
