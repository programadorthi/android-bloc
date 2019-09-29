package br.com.programadorthi.login.bloc

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.login.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class AuthenticationBloc(
    eventScope: CoroutineScope,
    private val userRepository: UserRepository
) : AndroidBloc<AuthenticationEvent, AuthenticationState>(eventScope) {

    override val initialState: AuthenticationState
        get() = AuthenticationState.AuthenticationUninitialized

    override suspend fun FlowCollector<AuthenticationState>.mapEventToState(
        event: AuthenticationEvent
    ) {
        when (event) {
            is AuthenticationEvent.AppStarted -> {
                val state = if (userRepository.hasToken()) {
                    AuthenticationState.AuthenticationAuthenticated
                } else {
                    AuthenticationState.AuthenticationUnauthenticated
                }
                emit(state)
            }
            is AuthenticationEvent.LoggedOut -> {
                emit(AuthenticationState.AuthenticationLoading)
                userRepository.deleteToken()
                emit(AuthenticationState.AuthenticationUnauthenticated)
            }
            is AuthenticationEvent.AppStopped -> {
                emit(AuthenticationState.AuthenticationLoading)
            }
        }
    }
}
