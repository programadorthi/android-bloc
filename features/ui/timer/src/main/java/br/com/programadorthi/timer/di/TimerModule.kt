package br.com.programadorthi.timer.di

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.timer.bloc.TimerBloc
import br.com.programadorthi.timer.bloc.TimerEvent
import br.com.programadorthi.timer.bloc.TimerState
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TIMER_BLOC = "TIMER_BLOC"

val timerModule = module {

    viewModel<AndroidBloc<TimerEvent, TimerState>>(named(TIMER_BLOC)) { TimerBloc(get()) }
}
