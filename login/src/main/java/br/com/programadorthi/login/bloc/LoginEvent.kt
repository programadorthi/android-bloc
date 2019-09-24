package br.com.programadorthi.login.bloc

sealed class LoginEvent {
    data class LoginButtonPressed(
        val username: String,
        val password: String
    ) : LoginEvent()
}