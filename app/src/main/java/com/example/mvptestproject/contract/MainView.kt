package com.example.mvptestproject.contract

import com.example.mvptestproject.domain.models.WeatherForecast
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun setUpUi()

    fun setListeners()

    fun showData(data: WeatherForecast)

    fun showLoading(isLoading: Boolean)
}
