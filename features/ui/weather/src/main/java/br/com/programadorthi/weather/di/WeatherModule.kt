package br.com.programadorthi.weather.di

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.weather.RetrofitBuilder
import br.com.programadorthi.weather.WeatherRepository
import br.com.programadorthi.weather.WeatherService
import br.com.programadorthi.weather.bloc.WeatherBloc
import br.com.programadorthi.weather.bloc.WeatherEvent
import br.com.programadorthi.weather.bloc.WeatherState
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val WEATHER_BLOC = "WEATHER_BLOC"

private const val RETROFIT_WEATHER = "RETROFIT_WEATHER"

val weatherModule = module {

    single(named(RETROFIT_WEATHER)) {
        RetrofitBuilder.provide()
    }

    single {
        get<Retrofit>(named(RETROFIT_WEATHER)).create(WeatherService::class.java)
    }

    single { WeatherRepository(get()) }

    viewModel<AndroidBloc<WeatherEvent, WeatherState>>(named(WEATHER_BLOC)) {
        WeatherBloc(get(), get())
    }
}
