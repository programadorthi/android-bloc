package br.com.programadorthi.androidbloc.bloc

import br.com.programadorthi.bloc.BlocInterceptor
import br.com.programadorthi.bloc.Transition
import com.orhanobut.logger.Logger

class MainBloc : BlocInterceptor {
    override fun onError(cause: Throwable) {
        Logger.e(cause, ">>>>> MainBloc onError")
    }

    override fun <Event> onEvent(event: Event) {
        Logger.i(">>>>> MainBloc onEvent: $event")
    }

    override fun <Event, State> onTransition(transition: Transition<Event, State>) {
        Logger.d(">>>>> MainBloc onTransition: $transition")
    }
}