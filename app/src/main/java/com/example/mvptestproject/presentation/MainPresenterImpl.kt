package com.example.mvptestproject.presentation

import com.example.mvptestproject.contract.MainPresenter
import com.example.mvptestproject.contract.MainView
import com.example.mvptestproject.data.network.ApiResult
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import com.example.mvptestproject.utils.Cities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenterImpl @Inject constructor(
    private val repository: WeatherForecastRepository,
) : MainPresenter, MvpPresenter<MainView>() {

    private val _weatherForecast = MutableStateFlow<WeatherForecast?>(null)
    private val weatherForecast = _weatherForecast

    override fun updateWeatherForecastByCity(city: String) {
        viewState.showLoading(true)
        getWeatherForecastByCity(city)

        weatherForecast.onEach {
            delay(1000)
            viewState.showLoading(false)
            viewState.showResult(it)
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    private fun getWeatherForecastByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            val coordinates = Cities.getCoordinatesByCity(city)
            coordinates?.let {
                when (val response = repository.getWeatherForecast(it)) {
                    is ApiResult.Success -> {
                        weatherForecast.emit(response.value)
                    }

                    is ApiResult.Error<*> -> {
                        weatherForecast.emit(null)
                    }
                }
            }
        }
    }
}
