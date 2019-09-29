package br.com.programadorthi.infinitelist

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitBuilder {
    private val contentType = MediaType.get("application/json")

    fun provide() = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}
