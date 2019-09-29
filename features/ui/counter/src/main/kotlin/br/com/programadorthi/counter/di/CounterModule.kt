package br.com.programadorthi.counter.di

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.counter.bloc.CounterBloc
import br.com.programadorthi.counter.bloc.CounterEvent
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val COUNTER_BLOC = "COUNTER_BLOC"

val counterModule = module {

    viewModel<AndroidBloc<CounterEvent, Int>>(named(COUNTER_BLOC)) {
        CounterBloc(
            get()
        )
    }
}
