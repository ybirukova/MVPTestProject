package com.example.mvptestproject.presentation

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import com.example.mvptestproject.R
import com.example.mvptestproject.databinding.ActivityMainBinding
import com.example.mvptestproject.domain.models.WeatherForecast
import com.example.mvptestproject.presentation.contract.MainView
import com.example.mvptestproject.utils.NonFilterArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenterProvider: Provider<MainPresenterImpl>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setListeners()
    }

    override fun setUpUi() {
        val cities = resources.getStringArray(R.array.cities).toList()
        val adapter = NonFilterArrayAdapter(this, R.layout.list_item, cities)
        with(binding) {
            autoCompleteTxt.setAdapter(adapter)
            autoCompleteTxt.setOnDismissListener {
                if (autoCompleteTxt.text.isNullOrEmpty()) {
                    dropdownMenu.clearFocus()
                }
            }
        }
    }

    override fun setListeners() {
        with(binding) {
            autoCompleteTxt.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                if (id.toInt() == 0) {
                    dropdownMenu.clearFocus()
                }
                presenter.updateWeatherForecastByCity(selectedItem)
            }
        }
    }

    override fun showResult(data: WeatherForecast?) {
        with(binding) {
            data?.let {
                llWeather.isVisible = true
                description.text = resources.getString(
                    R.string.description,
                    it.weather.description,
                )
                temperature.text = resources.getString(
                    R.string.temperature,
                    it.main.temperature,
                )
                feelsLike.text = resources.getString(
                    R.string.feels_like,
                    it.main.feelsLike,
                )
                temperatureMin.text = resources.getString(
                    R.string.temperature_min,
                    it.main.temperatureMin,
                )
                temperatureMax.text = resources.getString(
                    R.string.temperature_max,
                    it.main.temperatureMax,
                )
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        with(binding) {
            llWeather.visibility = if (isLoading) INVISIBLE else VISIBLE
            progressBar.visibility = if (isLoading) VISIBLE else INVISIBLE
        }
    }

    override fun showError(isError: Boolean) {
        with(binding) {
            llWeather.visibility = if (isError) INVISIBLE else VISIBLE
            errorText.visibility = if (isError) VISIBLE else INVISIBLE
        }
    }
}
