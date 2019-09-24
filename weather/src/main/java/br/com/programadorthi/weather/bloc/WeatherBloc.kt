package br.com.programadorthi.weather.bloc

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.weather.data.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class WeatherBloc(
    eventScope: CoroutineScope,
    private val weatherRepository: WeatherRepository
) : Bloc<WeatherEvent, WeatherState>(eventScope) {

    override val initialState: WeatherState
        get() = WeatherState.WeatherEmpty

    override suspend fun FlowCollector<WeatherState>.mapEventToState(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.FetchWeathe -> {
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