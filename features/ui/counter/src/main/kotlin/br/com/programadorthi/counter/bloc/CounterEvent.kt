package br.com.programadorthi.counter.bloc

sealed class CounterEvent {
    object Decrement : CounterEvent()
    object Increment : CounterEvent()
}
