package com.example.mvptestproject.data.network

import com.example.mvptestproject.data.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    companion object {
        const val APP_ID = "a29804c66cdd08ac18a2f47a8e93c5ee"
    }

    @GET("weather?appid=$APP_ID")
    suspend fun getWeatherForecastByCity(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): Response<WeatherForecastResponse>
}
