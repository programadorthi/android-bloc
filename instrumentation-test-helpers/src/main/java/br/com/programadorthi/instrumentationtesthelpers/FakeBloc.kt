package br.com.programadorthi.instrumentationtesthelpers

import br.com.programadorthi.bloc.UnawareBloc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class FakeBloc<Event, State>(
    eventScope: CoroutineScope,
    private val initialFakeState: State
) : UnawareBloc<Event, State>(eventScope) {

    override val initialState: State
        get() = initialFakeState

    override suspend fun FlowCollector<State>.mapEventToState(event: Event) {}
}