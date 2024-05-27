package com.example.mvptestproject.data

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
)

data class Weather(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
)

data class Main(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val temperatureMin: Double,
    @SerializedName("temp_max") val temperatureMax: Double,
)
