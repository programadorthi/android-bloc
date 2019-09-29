package br.com.programadorthi.login

interface UserRepository {
    suspend fun authenticate(username: String, password: String): Boolean

    suspend fun deleteToken()

    suspend fun hasToken(): Boolean
}
