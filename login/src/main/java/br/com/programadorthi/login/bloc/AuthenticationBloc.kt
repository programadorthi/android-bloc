package br.com.programadorthi.login.bloc

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.login.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class AuthenticationBloc(
    eventScope: CoroutineScope,
    private val userRepository: UserRepository
) : Bloc<AuthenticationEvent, AuthenticationState>(eventScope) {

    override val initialState: AuthenticationState
        get() = AuthenticationState.AuthenticationUninitialized

    override suspend fun FlowCollector<AuthenticationState>.mapEventToState(event: AuthenticationEvent) {
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