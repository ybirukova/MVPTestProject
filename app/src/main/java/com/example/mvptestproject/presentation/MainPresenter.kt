package com.example.mvptestproject.presentation

import com.example.mvptestproject.contract.MainView
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import com.example.mvptestproject.utils.Cities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val repository: WeatherForecastRepository,
) : MvpPresenter<MainView>() {

    private var weatherForecast: WeatherForecast? = null

    fun onCreateView() {
        viewState.setUpUi()
        viewState.setListeners()
    }

    fun getWeatherForecast(city: String) {
        viewState.showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            updateWeatherForecastByCity(city)

            withContext(Dispatchers.Main) {
                viewState.showLoading(false)
                weatherForecast?.let { viewState.showData(it) }
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
