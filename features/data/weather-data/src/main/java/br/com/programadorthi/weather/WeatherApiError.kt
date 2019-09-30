package br.com.programadorthi.weather

sealed class WeatherApiError : Exception() {
    object CityNotFound : WeatherApiError()
    object NoResult : WeatherApiError()
}
