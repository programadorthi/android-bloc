package br.com.programadorthi.weather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.programadorthi.weather.R

class WeatherSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, WeatherSettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
