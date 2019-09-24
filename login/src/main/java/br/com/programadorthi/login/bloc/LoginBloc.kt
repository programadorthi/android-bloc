package br.com.programadorthi.login.bloc

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.login.domain.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class LoginBloc(
    eventScope: CoroutineScope,
    private val loginUseCase: LoginUseCase
) : Bloc<LoginEvent, LoginState>(eventScope) {

    override val initialState: LoginState
        get() = LoginState.LoginInitial

    override suspend fun FlowCollector<LoginState>.mapEventToState(event: LoginEvent) {
        if (event is LoginEvent.LoginButtonPressed) {
            emit(LoginState.LoginLoading)

            try {
                val result = loginUseCase.doLogin(event.username, event.password)
                if (result) {
                    emit(LoginState.LoginSuccess)
                } else {
                    emit(LoginState.LoginFailed)
                }
            } catch (ex: Exception) {
                emit(LoginState.LoginFailure(ex))
            }
        }
    }
}