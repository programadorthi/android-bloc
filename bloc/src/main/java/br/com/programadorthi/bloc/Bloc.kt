package br.com.programadorthi.bloc

import androidx.lifecycle.ViewModel
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

abstract class Bloc<Event, State>(
    private val eventScope: CoroutineScope
) : EventDispatcher<Event>, StateConsumer<State>, ViewModel() {

    override val currentState: State
        get() = stateChannel.value

    override val state: Flow<State> by lazy { stateChannel.asFlow() }

    private val stateChannel by lazy { ConflatedBroadcastChannel(initialState) }

    private val eventChannel = Channel<Event>()

    init {
        startCollectState()
    }

    override fun onCleared() {
        eventScope.cancel()
        super.onCleared()
    }

    override fun dispatch(event: Event) {
        try {
            BlocInterceptor.callOnEvent(event)
            onEvent(event)
            handleEvent(event)
        } catch (ex: Throwable) {
            handleError(ex)
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

    private fun handleError(cause: Throwable) {
        BlocInterceptor.callOnError(cause)
        onError(cause)
    }

    private fun handleEvent(event: Event) {
        eventScope.launch {
            if (!computeEvent(event)) {
                return@launch
            }
            eventChannel.send(transformEvent(event))
        }
    }

    private suspend fun handleEventAndState(event: Event, state: State) {
        if (!computeState(state)) {
            return
        }
        val nextState = transformState(state)
        if (nextState == currentState) {
            return
        }
        doTransition(event, nextState)
        stateChannel.send(nextState)
    }

    private fun doTransition(event: Event, nextState: State) {
        val transition = Transition(
            currentState = currentState,
            event = event,
            nextState = nextState
        )
        BlocInterceptor.callOnTransition(transition)
        onTransition(transition)
    }

    private fun startCollectState() {
        eventScope.launch {
            for (event in eventChannel) {
                try {
                    flow<State> {
                        mapEventToState(event)
                    }.collect {
                        handleEventAndState(event, it)
                    }
                } catch (ex: Throwable) {
                    handleError(ex)
                }
            }
        }
    }
}