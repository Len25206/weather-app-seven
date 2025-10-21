package com.exam.weather_app_seven.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.exam.weather_app_seven.mvvm.model.Weather
import com.exam.weather_app_seven.mvvm.repository.WeatherRepository
import com.exam.weather_app_seven.retrofit.client.WeatherClientRetrofit
import com.exam.weather_app_seven.retrofit.pojo.WeatherResponse
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val weather = weatherRepository.getWeather()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()
    private val weatherService = WeatherClientRetrofit.instance

    fun weatherService(
        lat: String,
        lon: String,
        appid: String
    ) {
        Log.e("WeatherViewModel", "weatherService called $appid")
        weatherService.getWeather(
            lat = lat,
            lon = lon,
            appid = appid
        ).enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(
                call: Call<WeatherResponse?>,
                response: Response<WeatherResponse?>
            ) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                Log.d("WeatherResponse", gson.toJson(response.body()))

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        weatherRepository.setWeather(
                            Weather(
                                lon = body.coord?.lon ?: 0.0,
                                lat = body.coord?.lat ?: 0.0,
                                weatherMain = body.weather?.getOrNull(0)?.main ?: "",
                                weatherDescription = body.weather?.getOrNull(0)?.description ?: "",
                                mainTemp = body.main?.temp ?: 0.0,
                                mainFeelsLike = body.main?.feels_like ?: 0.0,
                                mainTempMin = body.main?.temp_min ?: 0.0,
                                mainTempMax = body.main?.temp_max ?: 0.0,
                                windSpeed = body.wind?.speed ?: 0.0,
                                cloudsAll = body.clouds?.all ?: 0,
                                visibility = body.visibility ?: 0,
                                locationName = body.name ?: "",
                                sysCountry = body.sys?.country ?: "",
                                sysSunrise = body.sys?.sunrise?.toString() ?: "",
                                sysSunset = body.sys?.sunset?.toString() ?: "",
                                pressure = body.main?.pressure ?: 0,
                                humidity = body.main?.humidity ?: 0,
                                iconCode = body.weather?.getOrNull(0)?.icon ?: ""
                            )
                        )
                    }
                } else {
                    Log.e("Error response :: ", "RESPONSE :: ${gson.toJson(response.code())}")
                    _isError.value = true
                }

            }

            override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                Log.e("WeatherError", t.message ?: "Unknown error")
            }
        })
    }

    fun resetHandlerError() {
        _isError.value = false
    }


}