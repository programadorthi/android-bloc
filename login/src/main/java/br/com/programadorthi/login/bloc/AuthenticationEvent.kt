package br.com.programadorthi.login.bloc

sealed class AuthenticationEvent {
    object AppStarted : AuthenticationEvent()

    object AppStopped: AuthenticationEvent()

    data class LoggedIn(val token: String) : AuthenticationEvent()

    object LoggedOut : AuthenticationEvent()
}