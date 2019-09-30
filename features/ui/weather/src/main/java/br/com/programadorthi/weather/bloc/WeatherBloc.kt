package br.com.programadorthi.weather.bloc

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.weather.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class WeatherBloc(
    eventScope: CoroutineScope,
    private val weatherRepository: WeatherRepository
) : AndroidBloc<WeatherEvent, WeatherState>(eventScope) {

    override val initialState: WeatherState
        get() = WeatherState.WeatherEmpty

    override suspend fun FlowCollector<WeatherState>.mapEventToState(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.FetchWeather -> {
                emit(WeatherState.WeatherLoading)
                try {
                    val weather = weatherRepository.getWeather(event.city)
                    emit(WeatherState.WeatherLoaded(weather = weather))
                } catch (ex: Exception) {
                    onError(ex)
                    emit(WeatherState.WeatherError(ex))
                }
            }
        }
    }
}
