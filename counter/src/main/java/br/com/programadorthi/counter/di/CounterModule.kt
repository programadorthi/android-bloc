package br.com.programadorthi.counter.di

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.counter.bloc.CounterBloc
import br.com.programadorthi.counter.bloc.CounterEvent
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val COUNTER_BLOC = "COUNTER_BLOC"

val counterModule = module {

    viewModel<Bloc<CounterEvent, Int>>(named(COUNTER_BLOC)) {
        CounterBloc(
            get()
        )
    }

}