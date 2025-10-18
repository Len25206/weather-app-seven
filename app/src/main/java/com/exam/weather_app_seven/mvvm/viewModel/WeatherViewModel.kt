package com.exam.weather_app_seven.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import com.exam.weather_app_seven.mvvm.model.Weather
import com.exam.weather_app_seven.mvvm.repository.WeatherRepository
import com.exam.weather_app_seven.retrofit.client.WeatherClientRetrofit

import com.exam.weather_app_seven.retrofit.pojo.WeatherResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val weather = weatherRepository.getWeather()
    private val weatherService = WeatherClientRetrofit.instance

    init {
        weatherService()
    }

    fun weatherService() {
        weatherService.getWeather(
            lat = "10.3733001",
            lon = "124.7488169",
            appid = "bb1f7e5036ab3b59255d9fc1caa97792"
        ).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse?>,
                response: Response<WeatherResponse?>
            ) {
                val gson: Gson = GsonBuilder().setPrettyPrinting().create()
                val data: String = gson.toJson(response.body())
                Log.d("data :: ", data)

                weatherRepository.setWeather(
                    Weather(
                        lon = response.body()?.coord?.lon ?: 0.0,
                        lat = response.body()?.coord?.lat ?: 0.0,
                        weatherMain = response.body()?.weather?.get(0)?.main ?: "",
                        weatherDescription = response.body()?.weather?.get(0)?.description ?: "",
                        mainTemp = response.body()?.main?.temp ?: 0.0,
                        mainFeelsLike = response.body()?.main?.feels_like ?: 0.0,
                        mainTempMin = response.body()?.main?.temp_min ?: 0.0,
                        mainTempMax = response.body()?.main?.temp_max ?: 0.0,
                        windSpeed = response.body()?.wind?.speed ?: 0.0,
                        cloudsAll = response.body()?.clouds?.all ?: 0,
                        visibility = response.body()?.visibility ?: 0,
                        locationName = response.body()?.name ?: "",
                        sysCountry = response.body()?.sys?.country ?: "",
                        sysSunrise = response.body()?.sys?.sunrise.toString(),
                        sysSunset = response.body()?.sys?.sunset.toString(),
                        pressure = response.body()?.main?.pressure,
                        humidity = response.body()?.main?.humidity,
                    )
                )
            }

            override fun onFailure(
                call: Call<WeatherResponse?>,
                t: Throwable
            ) {
                Log.d("data :: ", t.message.toString())
            }
        })

    }


}