package com.exam.weather_app_seven.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_app_seven.database.entity.WeatherHistoryEntity
import com.exam.weather_app_seven.mvvm.model.WeatherHistory
import com.exam.weather_app_seven.mvvm.repository.WeatherHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val weatherHistoryRepository: WeatherHistoryRepository
) : ViewModel() {
    val weatherHistory = weatherHistoryRepository.weatherHistory

    fun setWeatherHistory(userId: String) {
        viewModelScope.launch {
            weatherHistoryRepository.setWeatherHistory(userId)
        }
    }

    fun insertWeatherHistory(weatherHistory: WeatherHistory) {
        viewModelScope.launch {
            weatherHistoryRepository.insertWeatherHistory(weatherHistory)
        }
    }
}