package br.com.programadorthi.timer.bloc

import br.com.programadorthi.androidbloc.AndroidBloc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class TimerBloc(
    private val eventScope: CoroutineScope
) : AndroidBloc<TimerEvent, TimerState>(eventScope) {

    override val initialState: TimerState
        get() = TimerState.Ready(DURATION_IN_MILLIS)

    private lateinit var tickerChannel: ReceiveChannel<Unit>

    override fun onCleared() {
        releaseTicker()
        super.onCleared()
    }

    override suspend fun FlowCollector<TimerState>.mapEventToState(event: TimerEvent) {
        when (event) {
            is TimerEvent.Start -> mapStartEventToState(event)
            is TimerEvent.Tick -> mapTickEventToState(event)
            is TimerEvent.Pause -> mapPauseEventToState()
            is TimerEvent.Resume -> mapResumeEventToState()
            is TimerEvent.Reset -> mapResetEventToState()
        }
    }

    private suspend fun FlowCollector<TimerState>.mapStartEventToState(event: TimerEvent.Start) {
        emit(TimerState.Running(duration = event.duration))
        releaseTicker()
        initTicker(duration = event.duration)
    }

    private suspend fun FlowCollector<TimerState>.mapTickEventToState(event: TimerEvent.Tick) {
        if (event.duration > 0) {
            emit(TimerState.Running(duration = event.duration))
            return
        }
        emit(TimerState.Finished)
    }

    private suspend fun FlowCollector<TimerState>.mapPauseEventToState() {
        val state = currentState
        if (state is TimerState.Running) {
            emit(TimerState.Paused(duration = state.duration))
        }
    }

    private suspend fun FlowCollector<TimerState>.mapResumeEventToState() {
        val state = currentState
        if (state is TimerState.Paused) {
            emit(TimerState.Running(duration = state.duration))
        }
    }

    private suspend fun FlowCollector<TimerState>.mapResetEventToState() {
        releaseTicker()
        emit(TimerState.Ready(duration = DURATION_IN_MILLIS))
    }

    private fun initTicker(duration: Long) {
        eventScope.launch {
            tickerChannel = ticker(
                delayMillis = TICKER_INTERVAL_IN_MILLIS,
                initialDelayMillis = 0
            )

            var currentDuration = duration

            for (tick in tickerChannel) {
                if (currentState is TimerState.Paused) {
                    continue
                }

                currentDuration -= TICKER_INTERVAL_IN_MILLIS
                dispatch(TimerEvent.Tick(duration = currentDuration))

                if (currentDuration > 0) {
                    continue
                }

                releaseTicker()
            }
        }
    }

    private fun releaseTicker() {
        if (::tickerChannel.isInitialized) {
            tickerChannel.cancel()
        }
    }

    companion object {
        private const val TICKER_INTERVAL_IN_MILLIS = 1000L
        private const val DURATION_IN_MILLIS = 10L * TICKER_INTERVAL_IN_MILLIS
    }
}
