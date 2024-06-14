package com.example.mvptestproject.presentation

import com.example.mvptestproject.data.network.ApiResult
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import com.example.mvptestproject.presentation.contract.MainPresenter
import com.example.mvptestproject.presentation.contract.MainView
import com.example.mvptestproject.utils.Cities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class MainPresenterImpl @Inject constructor(
    private val repository: WeatherForecastRepository,
) : MainPresenter, MvpPresenter<MainView>() {

    private val _weatherForecast = MutableSharedFlow<WeatherForecast?>(1)
    private val weatherForecast = _weatherForecast

    private val _isError = MutableSharedFlow<Boolean>(1)
    private val isError = _isError

    init {
        weatherForecast.onEach {
            viewState.showLoading(false)
            viewState.showResult(it)
        }.launchIn(presenterScope)

        isError.onEach {
            viewState.showError(it)
        }
    }

    override fun updateWeatherForecastByCity(city: String) {
        viewState.showLoading(true)
        getWeatherForecastByCity(city)
    }

    private fun getWeatherForecastByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            val coordinates = Cities.getCoordinatesByCity(city)
            coordinates?.let {
                when (val response = repository.getWeatherForecast(it)) {
                    is ApiResult.Success -> {
                        weatherForecast.emit(response.value)
                        isError.emit(false)
                    }

                    is ApiResult.Error<*> -> {
                        weatherForecast.emit(null)
                        isError.emit(true)
                    }
                }
            }
        }
    }
}
