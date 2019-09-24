package br.com.programadorthi.weather.di

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.weather.bloc.WeatherBloc
import br.com.programadorthi.weather.bloc.WeatherEvent
import br.com.programadorthi.weather.bloc.WeatherState
import br.com.programadorthi.weather.data.WeatherRepository
import br.com.programadorthi.weather.data.WeatherService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val WEATHER_BLOC = "WEATHER_BLOC"

private const val RETROFIT_WEATHER = "RETROFIT_WEATHER"

private val contentType = MediaType.get("application/json")

val weatherModule = module {

    single<Retrofit>(named(RETROFIT_WEATHER)) {
        Retrofit.Builder()
            .baseUrl("https://www.metaweather.com")
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
    }

    single<WeatherService> {
        get<Retrofit>(named(RETROFIT_WEATHER)).create(WeatherService::class.java)
    }

    single { WeatherRepository(get()) }

    viewModel<Bloc<WeatherEvent, WeatherState>>(named(WEATHER_BLOC)) { WeatherBloc(get(), get()) }

}