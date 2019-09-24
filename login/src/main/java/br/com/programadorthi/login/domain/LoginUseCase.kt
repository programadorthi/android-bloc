package br.com.programadorthi.login.domain

import br.com.programadorthi.login.data.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {

    @Throws(LoginDomainError::class)
    suspend fun doLogin(username: String, password: String): Boolean {
        when {
            username.isBlank() -> throw LoginDomainError.UsernameEmpty
            //username.trim().length < 6 -> throw LoginDomainError.UsernameInvalid
            password.isBlank() -> throw LoginDomainError.PasswordEmpty
            //password.trim().length < 8 -> throw LoginDomainError.PasswordInvalid
        }
        return userRepository.authenticate(username, password)
    }
}