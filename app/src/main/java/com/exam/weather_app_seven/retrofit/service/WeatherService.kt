package com.exam.weather_app_seven.retrofit.service

import android.util.Log
import com.exam.weather_app_seven.retrofit.client.WeatherClientRetrofit
import com.exam.weather_app_seven.retrofit.pojo.WeatherResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun weatherService() {
    WeatherClientRetrofit.instance.getWeather(
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
        }

        override fun onFailure(
            call: Call<WeatherResponse?>,
            t: Throwable
        ) {
            Log.d("data :: ", t.message.toString())
        }

    })
}