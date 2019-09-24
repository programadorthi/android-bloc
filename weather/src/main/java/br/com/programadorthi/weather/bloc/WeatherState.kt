package br.com.programadorthi.weather.bloc

import br.com.programadorthi.weather.data.WeatherRaw

sealed class WeatherState {
    object WeatherEmpty : WeatherState()

    object WeatherLoading : WeatherState()

    data class WeatherLoaded(val weather: WeatherRaw) : WeatherState()

    data class WeatherError(val error: Exception) : WeatherState()
}