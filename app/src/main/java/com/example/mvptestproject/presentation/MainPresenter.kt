package com.example.mvptestproject.presentation

import com.example.mvptestproject.contract.MainContract
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import com.example.mvptestproject.utils.Cities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val repository: WeatherForecastRepository,
) : MainContract.Presenter {

    private var weatherForecast: WeatherForecast? = null

    override fun fetchData() {
        weatherForecast?.let {
            view.showLoading(false)
            view.showData(it)
        }
    }

    override fun onCityClicked(city: String) {
        view.showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            updateWeatherForecastByCity(city)

            withContext(Dispatchers.Main) {
                fetchData()
            }
        }
    }

    private suspend fun updateWeatherForecastByCity(city: String) {
        val coordinates = Cities.getCoordinatesByCity(city)
        coordinates?.let {
            weatherForecast = repository.getWeatherForecast(it)
        }
    }
}
