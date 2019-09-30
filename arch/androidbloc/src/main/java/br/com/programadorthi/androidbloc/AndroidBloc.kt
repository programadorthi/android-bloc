package br.com.programadorthi.androidbloc

import androidx.lifecycle.ViewModel
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.bloc.BlocInterceptor
import br.com.programadorthi.bloc.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class AndroidBloc<Event, State>(
    private val eventScope: CoroutineScope
) : Bloc<Event, State>, ViewModel() {

    override val currentState: State
        get() = stateChannel.value

    override val state: Flow<State> by lazy { stateChannel.asFlow() }

    private val stateChannel by lazy { ConflatedBroadcastChannel(initialState) }

    private val eventChannel = Channel<Event>()

    init {
        startCollectState()
    }

    override fun onCleared() {
        try {
            eventScope.cancel()
        } finally {
            super.onCleared()
        }
    }

    override fun dispatch(event: Event) {
        try {
            BlocInterceptor.callOnEvent(event)
            onEvent(event)
            eventScope.launch {
                if (!computeEvent(event)) {
                    return@launch
                }
                eventChannel.send(transformEvent(event))
            }
        } catch (cause: Throwable) {
            BlocInterceptor.callOnError(cause)
            onError(cause)
        }
    }

    protected abstract val initialState: State

    protected abstract suspend fun FlowCollector<State>.mapEventToState(event: Event)

    protected open fun onError(cause: Throwable) {}

    protected open fun onEvent(event: Event) {}

    protected open fun onTransition(transition: Transition<Event, State>) {}

    protected open suspend fun computeEvent(event: Event): Boolean = true

    protected open suspend fun computeState(state: State): Boolean = true

    protected open suspend fun transformEvent(event: Event): Event = event

    protected open suspend fun transformState(state: State): State = state

    private fun startCollectState() {
        eventScope.launch {
            for (event in eventChannel) {
                try {
                    flow<State> {
                        mapEventToState(event)
                    }.collect { state ->
                        if (!computeState(state)) {
                            return@collect
                        }
                        val nextState = transformState(state)
                        if (nextState == currentState) {
                            return@collect
                        }
                        val transition = Transition(
                            currentState = currentState,
                            event = event,
                            nextState = nextState
                        )
                        BlocInterceptor.callOnTransition(transition)
                        onTransition(transition)
                        stateChannel.send(nextState)
                    }
                } catch (cause: Throwable) {
                    BlocInterceptor.callOnError(cause)
                    onError(cause)
                }
            }
        }
    }
}
