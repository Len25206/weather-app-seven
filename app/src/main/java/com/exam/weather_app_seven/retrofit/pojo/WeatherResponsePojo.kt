package com.exam.weather_app_seven.retrofit.pojo

import com.google.gson.annotations.SerializedName
data class WeatherResponse(
    @SerializedName("coord")
    val coord: Coord? = Coord(),
    @SerializedName("weather")
    val weather: List<Weather>? = emptyList(),
    @SerializedName("base")
    val base: String? = "",
    @SerializedName("main")
    val main: Main? = Main(),
    @SerializedName("visibility")
    val visibility: Int? = 0,
    @SerializedName("wind")
    val wind: Wind? = Wind(),
    @SerializedName("clouds")
    val clouds: Clouds? = Clouds(),
    @SerializedName("dt")
    val dt: Long? = 0L,
    @SerializedName("sys")
    val sys: Sys? = Sys(),
    @SerializedName("timezone")
    val timezone: Int? = 0,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("cod")
    val cod: Int? = 0
)

data class Coord(
    @SerializedName("lon")
    val lon: Double? = 0.0,
    @SerializedName("lat")
    val lat: Double? = 0.0
)

data class Weather(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("main")
    val main: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("icon")
    val icon: String? = ""
)

data class Main(
    @SerializedName("temp")
    val temp: Double? = 0.0,
    @SerializedName("feels_like")
    val feels_like: Double? = 0.0,
    @SerializedName("temp_min")
    val temp_min: Double? = 0.0,
    @SerializedName("temp_max")
    val temp_max: Double? = 0.0,
    @SerializedName("pressure")
    val pressure: Int? = 0,
    @SerializedName("humidity")
    val humidity: Int? = 0,
    @SerializedName("sea_level")
    val sea_level: Int? = 0,
    @SerializedName("grnd_level")
    val grnd_level: Int? = 0
)

data class Wind(
    @SerializedName("speed")
    val speed: Double? = 0.0,
    @SerializedName("deg")
    val deg: Int? = 0,
    @SerializedName("gust")
    val gust: Double? = 0.0
)

data class Clouds(
    @SerializedName("all")
    val all: Int? = 0
)

data class Sys(
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("sunrise")
    val sunrise: Long? = 0L,
    @SerializedName("sunset")
    val sunset: Long? = 0L
)
