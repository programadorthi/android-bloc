package br.com.programadorthi.login.data

import android.content.SharedPreferences
import kotlinx.coroutines.delay

class UserRepository(private val preferences: SharedPreferences) {

    suspend fun authenticate(username: String, password: String): Boolean {
        delay(1000)
        preferences.edit().putString(TOKEN, "token").apply()
        return true
    }

    suspend fun deleteToken() {
        delay(1000)
        preferences.edit().remove(TOKEN).apply()
    }

    suspend fun hasToken(): Boolean {
        delay(1000)
        return !preferences.getString(TOKEN, "").isNullOrBlank()
    }

    private companion object {
        private const val TOKEN = "token"
    }
}