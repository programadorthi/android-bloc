package br.com.programadorthi.bloc

interface Bloc<Event, State> : EventDispatcher<Event>, StateConsumer<State>
