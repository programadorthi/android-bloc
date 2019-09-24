package br.com.programadorthi.weather.bloc

sealed class WeatherEvent {
    data class FetchWeathe(val city: String) : WeatherEvent()
}