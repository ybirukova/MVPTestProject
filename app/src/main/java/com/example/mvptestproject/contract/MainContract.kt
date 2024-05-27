package com.example.mvptestproject.contract

import com.example.mvptestproject.domain.models.WeatherForecast

interface MainContract {
    interface View {
        fun showData(data: WeatherForecast)
        fun showLoading(isLoading: Boolean)
    }

    interface Presenter {
        fun fetchData()
        fun onCityClicked(city: String)
    }
}
