package br.com.programadorthi.login

sealed class LoginDomainError(message: String) : IllegalStateException(message) {
    object PasswordEmpty : LoginDomainError("Password is empty")
    object PasswordInvalid : LoginDomainError("Invalid password")
    object UsernameEmpty : LoginDomainError("Username is empty")
    object UsernameInvalid : LoginDomainError("Invalid username")
}
