package com.example.mvptestproject.domain.repository

import com.example.mvptestproject.domain.models.Coordinates
import com.example.mvptestproject.domain.models.WeatherForecast

interface WeatherForecastRepository {

    suspend fun getWeatherForecast(coordinates: Coordinates): WeatherForecast
}
