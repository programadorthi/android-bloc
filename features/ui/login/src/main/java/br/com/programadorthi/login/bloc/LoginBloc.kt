package br.com.programadorthi.login.bloc

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.login.LoginDomainError
import br.com.programadorthi.login.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class LoginBloc(
    eventScope: CoroutineScope,
    private val loginUseCase: LoginUseCase
) : AndroidBloc<LoginEvent, LoginState>(eventScope) {

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
            } catch (ex: LoginDomainError) {
                emit(LoginState.LoginFailure(ex))
            }
        }
    }
}
