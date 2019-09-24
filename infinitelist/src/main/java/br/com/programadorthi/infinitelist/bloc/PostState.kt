package br.com.programadorthi.infinitelist.bloc

import br.com.programadorthi.infinitelist.data.Post

sealed class PostState {
    object PostError : PostState()
    object PostLoading : PostState()
    object PostUninitialized : PostState()
    data class PostLoaded(
        val posts: List<Post>,
        val hasReachedMax: Boolean = false
    ) : PostState() {
        override fun toString(): String {
            if (posts.isEmpty()) return "PostLoaded(posts=$posts, hasReachedMax=$hasReachedMax)"
            return "PostLoaded(posts=${posts[0].id}-${posts.last().id}, hasReachedMax=$hasReachedMax)"
        }
    }
}