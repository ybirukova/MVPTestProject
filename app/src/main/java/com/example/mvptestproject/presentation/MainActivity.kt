package com.example.mvptestproject.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.mvptestproject.R
import com.example.mvptestproject.contract.MainView
import com.example.mvptestproject.data.repositories.WeatherForecastRepositoryImpl
import com.example.mvptestproject.databinding.ActivityMainBinding
import com.example.mvptestproject.domain.models.WeatherForecast
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repositoryImpl: WeatherForecastRepositoryImpl

    @InjectPresenter
    lateinit var presenter: MainPresenterImpl

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenterImpl {
        return MainPresenterImpl(repositoryImpl)
    }

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
        if (data != null) {
            with(binding) {
                description.text = resources.getString(
                    R.string.description,
                    data.weather.description,
                )
                temperature.text = resources.getString(
                    R.string.temperature,
                    data.main.temperature,
                )
                feelsLike.text = resources.getString(
                    R.string.feels_like,
                    data.main.feelsLike,
                )
                temperatureMin.text = resources.getString(
                    R.string.temperature_min,
                    data.main.temperatureMin,
                )
                temperatureMax.text = resources.getString(
                    R.string.temperature_max,
                    data.main.temperatureMax,
                )
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.isVisible = isLoading
            llWeather.isVisible = !isLoading
        }
    }
}
