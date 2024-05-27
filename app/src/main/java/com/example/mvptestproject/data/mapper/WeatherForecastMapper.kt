package com.example.mvptestproject.data.mapper

import com.example.mvptestproject.data.WeatherForecastResponse
import com.example.mvptestproject.domain.models.Main
import com.example.mvptestproject.domain.models.Weather
import com.example.mvptestproject.domain.models.WeatherForecast
import javax.inject.Inject

class WeatherForecastMapper @Inject constructor() {
    operator fun invoke(response: WeatherForecastResponse) = with(response) {
        WeatherForecast(
            weather = Weather(weather[0].main, weather[0].description),
            main = Main(
                main.temperature.kelvinToCelsius(),
                main.feelsLike.kelvinToCelsius(),
                main.temperatureMin.kelvinToCelsius(),
                main.temperatureMax.kelvinToCelsius(),
            ),
        )
    }
}

fun Double.kelvinToCelsius(): String = "${(this - 273.15).toInt()} °C"
