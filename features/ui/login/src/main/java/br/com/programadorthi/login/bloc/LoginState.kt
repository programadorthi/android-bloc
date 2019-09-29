package br.com.programadorthi.login.bloc

sealed class LoginState {
    object LoginFailed : LoginState()

    object LoginInitial : LoginState()

    object LoginLoading : LoginState()

    object LoginSuccess : LoginState()

    data class LoginFailure(val exception: Exception) : LoginState()
}
