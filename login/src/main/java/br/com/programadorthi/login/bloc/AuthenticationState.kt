package br.com.programadorthi.login.bloc

sealed class AuthenticationState {
    object AuthenticationUninitialized : AuthenticationState()

    object AuthenticationAuthenticated : AuthenticationState()

    object AuthenticationUnauthenticated : AuthenticationState()

    object AuthenticationLoading : AuthenticationState()
}