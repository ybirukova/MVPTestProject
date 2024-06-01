package com.example.mvptestproject.data.repositories

import com.example.mvptestproject.data.mapper.WeatherForecastMapper
import com.example.mvptestproject.data.network.ApiResult
import com.example.mvptestproject.data.network.WeatherForecastService
import com.example.mvptestproject.domain.models.Coordinates
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val service: WeatherForecastService,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {
    override suspend fun getWeatherForecast(coordinates: Coordinates): ApiResult<WeatherForecast> {
        return withContext(Dispatchers.IO) {
            val response = service.getWeatherForecastByCity(
                lat = coordinates.lat.toString(),
                lon = coordinates.lon.toString(),
            )
            if (response.isSuccessful) {
                (mapper(response.body()!!)) as ApiResult.Success
            } else {
                ApiResult.Error(null)
            }
        }
    }
}
