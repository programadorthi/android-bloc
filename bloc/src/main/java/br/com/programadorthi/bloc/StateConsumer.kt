package br.com.programadorthi.bloc

import kotlinx.coroutines.flow.Flow

interface StateConsumer<State> {
    val currentState: State

    val state: Flow<State>
}