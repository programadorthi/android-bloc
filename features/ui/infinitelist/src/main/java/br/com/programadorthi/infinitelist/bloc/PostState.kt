package br.com.programadorthi.infinitelist.bloc

sealed class PostState {
    object PostError : PostState()
    object PostLoading : PostState()
    object PostUninitialized : PostState()
    data class PostLoaded(
        val posts: List<br.com.programadorthi.infinitelist.Post>,
        val hasReachedMax: Boolean = false
    ) : PostState()
}
