package br.com.programadorthi.infinitelist.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    companion object {
        val LOADING = Post(
            userId = -1,
            id = -1,
            title = "",
            body = ""
        )
    }
}