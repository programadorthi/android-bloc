package br.com.programadorthi.weather.presentation

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import br.com.programadorthi.weather.R

class WeatherSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.weather_preferences, rootKey)
    }
}
