package br.com.programadorthi.weather

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("/api/location/search/")
    suspend fun getLocationId(@Query("query") query: String): List<WeatherCity>

    @GET("/api/location/{locationId}")
    suspend fun get(@Path("locationId") locationId: Int): WeatherRaw
}
