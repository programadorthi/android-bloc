package br.com.programadorthi.bloc

data class Transition<Event, State>(
    val currentState: State,
    val event: Event,
    val nextState: State
)