package br.com.programadorthi.weather

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitBuilder {
    private val contentType = MediaType.get("application/json")

    fun provide() = Retrofit.Builder()
        .baseUrl("https://www.metaweather.com")
        .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
        .build()
}
