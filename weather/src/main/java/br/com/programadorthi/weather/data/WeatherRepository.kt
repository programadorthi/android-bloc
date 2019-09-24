package br.com.programadorthi.weather.data

class WeatherRepository(
    private val weatherService: WeatherService
) {
    suspend fun getWeather(city: String): WeatherRaw {
        val cities = weatherService.getLocationId(query = city)
        if (cities.isEmpty()) throw WeatherApiError.CityNotFound
        val cityId = cities.first().locationId
        val weatherRaw = weatherService.get(locationId = cityId)
        if (weatherRaw.weathers.isEmpty()) throw WeatherApiError.NoResult
        return weatherRaw
    }
}