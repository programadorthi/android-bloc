package br.com.programadorthi.login.data

import android.content.SharedPreferences
import br.com.programadorthi.login.UserRepository
import kotlinx.coroutines.delay

class UserRepositoryImpl(private val preferences: SharedPreferences) : UserRepository {

    override suspend fun authenticate(username: String, password: String): Boolean {
        delay(DELAY)
        preferences.edit().putString(TOKEN, "token").apply()
        return true
    }

    override suspend fun deleteToken() {
        delay(DELAY)
        preferences.edit().remove(TOKEN).apply()
    }

    override suspend fun hasToken(): Boolean {
        delay(DELAY)
        return !preferences.getString(TOKEN, "").isNullOrBlank()
    }

    private companion object {
        private const val DELAY = 1000L
        private const val TOKEN = "token"
    }
}
