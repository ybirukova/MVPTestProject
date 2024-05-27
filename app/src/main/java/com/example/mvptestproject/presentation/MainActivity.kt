package com.example.mvptestproject.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.mvptestproject.R
import com.example.mvptestproject.contract.MainContract
import com.example.mvptestproject.databinding.ActivityMainBinding
import com.example.mvptestproject.domain.models.WeatherForecast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setListeners()
    }

    private fun setUpUi() {
        val cities = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(this, R.layout.list_item, cities)
        with(binding) {
            autoCompleteTxt.setAdapter(adapter)
            autoCompleteTxt.setOnDismissListener {
                if (autoCompleteTxt.text.isNullOrEmpty()) {
                    dropdownMenu.clearFocus()
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            autoCompleteTxt.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                if (id.toInt() == 0) {
                    dropdownMenu.clearFocus()
                }
                presenter.onCityClicked(selectedItem)
            }
        }
    }

    override fun showData(data: WeatherForecast) {
        with(binding) {
            data.let {
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
            progressBar.isVisible = isLoading
            llWeather.isVisible = !isLoading
        }
    }
}
