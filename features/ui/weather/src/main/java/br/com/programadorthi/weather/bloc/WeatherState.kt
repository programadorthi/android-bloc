package br.com.programadorthi.weather.bloc

sealed class WeatherState {
    object WeatherEmpty : WeatherState()

    object WeatherLoading : WeatherState()

    data class WeatherLoaded(val weather: br.com.programadorthi.weather.WeatherRaw) : WeatherState()

    data class WeatherError(val error: Exception) : WeatherState()
}
