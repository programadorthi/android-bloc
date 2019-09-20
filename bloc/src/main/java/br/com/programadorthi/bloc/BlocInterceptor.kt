package br.com.programadorthi.bloc

interface BlocInterceptor {

    fun onError(cause: Throwable)

    fun <Event> onEvent(event: Event)

    fun <Event, State> onTransition(transition: Transition<Event, State>)

    companion object {
        private lateinit var instance: BlocInterceptor

        fun initBlocInterceptor(bloc: BlocInterceptor) {
            check(!::instance.isInitialized) { "BlocInterceptor is already initialized" }
            instance = bloc
        }

        fun callOnError(cause: Throwable) {
            if (::instance.isInitialized) {
                instance.onError(cause)
            }
        }

        fun <Event> callOnEvent(event: Event) {
            if (::instance.isInitialized) {
                instance.onEvent(event)
            }
        }

        fun <Event, State> callOnTransition(transition: Transition<Event, State>) {
            if (::instance.isInitialized) {
                instance.onTransition(transition)
            }
        }
    }
}