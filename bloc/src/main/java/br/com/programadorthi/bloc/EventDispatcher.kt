package br.com.programadorthi.bloc

interface EventDispatcher<Event> {
    fun dispatch(event: Event)
}