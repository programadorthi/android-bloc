package br.com.programadorthi.counter.bloc

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.bloc.Transition
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class CounterBloc(eventScope: CoroutineScope) : Bloc<CounterEvent, Int>(eventScope) {

    override val initialState: Int = 0

    override suspend fun FlowCollector<Int>.mapEventToState(event: CounterEvent) {
        val nextState = when (event) {
            is CounterEvent.Decrement -> currentState - 1
            is CounterEvent.Increment -> currentState + 1
        }
        emit(nextState)
    }

    override fun onError(cause: Throwable) {
        super.onError(cause)
        Logger.e(cause, ">>>>> onError local")
    }

    override fun onEvent(event: CounterEvent) {
        super.onEvent(event)
        Logger.i(">>>>> onEvent local: $event")
    }

    override fun onTransition(transition: Transition<CounterEvent, Int>) {
        super.onTransition(transition)
        Logger.d(">>>>> onTransition local: $transition")
    }

}