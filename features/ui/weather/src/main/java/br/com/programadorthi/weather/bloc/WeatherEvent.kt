package br.com.programadorthi.weather.bloc

sealed class WeatherEvent {
    data class FetchWeather(val city: String) : WeatherEvent()
}
