package br.com.programadorthi.weather.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.weather.R
import br.com.programadorthi.weather.bloc.WeatherEvent
import br.com.programadorthi.weather.bloc.WeatherState
import br.com.programadorthi.weather.data.WeatherApiError
import br.com.programadorthi.weather.data.WeatherCondition
import br.com.programadorthi.weather.di.WEATHER_BLOC
import kotlinx.android.synthetic.main.activity_weather.cityTextView
import kotlinx.android.synthetic.main.activity_weather.currentTempTextView
import kotlinx.android.synthetic.main.activity_weather.formattedConditionTextView
import kotlinx.android.synthetic.main.activity_weather.imageView
import kotlinx.android.synthetic.main.activity_weather.maxTextView
import kotlinx.android.synthetic.main.activity_weather.minTextView
import kotlinx.android.synthetic.main.activity_weather.progressBar
import kotlinx.android.synthetic.main.activity_weather.searchButton
import kotlinx.android.synthetic.main.activity_weather.searchEditText
import kotlinx.android.synthetic.main.activity_weather.updatedTextView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.text.DateFormat
import kotlin.math.roundToInt

class WeatherActivity : AppCompatActivity() {

    private val weatherBloc: Bloc<WeatherEvent, WeatherState> by viewModel(named(WEATHER_BLOC))

    private val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        lifecycleScope.launch {
            weatherBloc.state.collect { state -> handleState(state) }
        }

        setupView()
    }

    override fun onResume() {
        super.onResume()
        resumeTemperature()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.weather_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, WeatherSettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleState(state: WeatherState) {
        when (state) {
            is WeatherState.WeatherEmpty -> {
                Toast.makeText(this@WeatherActivity, "Please enter a city name", Toast.LENGTH_SHORT)
                    .show()
            }
            is WeatherState.WeatherError -> {
                val text = when (state.error) {
                    is WeatherApiError.CityNotFound -> "City not found"
                    is WeatherApiError.NoResult -> "No result found"
                    else -> "Something went wrong!"
                }
                Toast.makeText(this@WeatherActivity, text, Toast.LENGTH_SHORT).show()
            }
            is WeatherState.WeatherLoaded -> {
                val weatherRaw = state.weather
                val weather = weatherRaw.weathers.first()
                cityTextView.text = weatherRaw.location
                updatedTextView.text = "Updated: ${dateFormat.format(weather.lastUpdated)}"
                formattedConditionTextView.text = weather.formmattedCondition

                setupTemperatures(state)
                mapWeatherCondition(weather.condition)
            }
        }

        searchButton.visibility =
            if (state is WeatherState.WeatherLoading) View.INVISIBLE else View.VISIBLE
        progressBar.visibility =
            if (state is WeatherState.WeatherLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupView() {
        searchButton.setOnClickListener {
            hideKeyboard()
            val city = searchEditText.text.trim().toString()
            weatherBloc.dispatch(WeatherEvent.FetchWeathe(city = city))
        }
    }

    private fun mapWeatherCondition(condition: WeatherCondition) {
        val weatherCondition = when (condition) {
            WeatherCondition.hail, WeatherCondition.snow, WeatherCondition.sleet -> R.drawable.ic_snowy
            WeatherCondition.heavyRain, WeatherCondition.lightRain, WeatherCondition.showers -> R.drawable.ic_rainy
            WeatherCondition.heavyCloud -> R.drawable.ic_cloudy
            WeatherCondition.thunderstorm -> R.drawable.ic_storm
            else -> R.drawable.ic_sun
        }
        imageView.setImageDrawable(ContextCompat.getDrawable(this, weatherCondition))
    }

    private fun resumeTemperature() {
        val state = weatherBloc.currentState
        if (state is WeatherState.WeatherLoaded) {
            setupTemperatures(state)
        }
    }

    private fun setupTemperatures(state: WeatherState.WeatherLoaded) {
        val weather = state.weather.weathers.first()
        currentTempTextView.text = "${convertTemperature(weather.temp)}º"
        minTextView.text = "Min: ${convertTemperature(weather.minTemp)}º"
        maxTextView.text = "Max: ${convertTemperature(weather.maxTemp)}º"
    }

    private fun convertTemperature(temp: Double): Int {
        val fahrenheitKey = getString(R.string.pref_weather_fahrenheit)
        val fahrenheit = preferences.getBoolean(fahrenheitKey, false)
        return if (fahrenheit) ((temp * 9 / 5) + 32).roundToInt() else temp.roundToInt()
    }

    private fun hideKeyboard() {
        val token = currentFocus?.windowToken ?: return
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
        inputMethodManager.hideSoftInputFromWindow(token, 0)
    }
}