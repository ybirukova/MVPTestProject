package com.example.mvptestproject.domain.models

data class WeatherForecast(
    val weather: Weather,
    val main: Main,
)

data class Weather(
    val main: String,
    val description: String,
)

data class Main(
    val temperature: String,
    val feelsLike: String,
    val temperatureMin: String,
    val temperatureMax: String,
)
