package br.com.programadorthi.weather.data

sealed class WeatherApiError : Exception() {
    object CityNotFound : WeatherApiError()
    object NoResult : WeatherApiError()
}