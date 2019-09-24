package br.com.programadorthi.infinitelist.bloc

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.infinitelist.data.PostService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector

class PostBloc(
    private val eventScope: CoroutineScope,
    private val postService: PostService
) : Bloc<PostEvent, PostState>(eventScope) {

    private lateinit var debounce: Deferred<Boolean>

    override val initialState: PostState
        get() = PostState.PostUninitialized

    override suspend fun FlowCollector<PostState>.mapEventToState(event: PostEvent) {
        when (event) {
            is PostEvent.Fetch.FetchFromPrevious -> mapFetchFromPreviousEventToState(event)
            is PostEvent.Fetch.FetchFromZero -> mapFetchInitialEventToState(event)
        }
    }

    override suspend fun computeEvent(event: PostEvent): Boolean {
        if (::debounce.isInitialized) {
            debounce.cancel()
        }
        debounce = eventScope.async {
            delay(500)
            super.computeEvent(event)
        }
        return try {
            debounce.await()
        } catch (ex: CancellationException) {
            Logger.e(ex, ">>>>> debounce cancelled")
            false
        }
    }

    override fun onError(cause: Throwable) {
        Logger.e(cause, ">>>>> onError local")
    }

    private suspend fun FlowCollector<PostState>.mapFetchFromPreviousEventToState(
        event: PostEvent.Fetch.FetchFromPrevious
    ) {
        try {
            when (val state = currentState) {
                is PostState.PostLoaded -> fetchNewPosts(event, state)
                is PostState.PostUninitialized -> dispatch(PostEvent.Fetch.FetchFromZero())
            }
        } catch (ex: Throwable) {
            onError(ex)
            emit(PostState.PostError)
        }
    }

    private suspend fun FlowCollector<PostState>.mapFetchInitialEventToState(
        event: PostEvent.Fetch.FetchFromZero
    ) {
        try {
            fetchInitialPosts(event)
        } catch (ex: Throwable) {
            onError(ex)
            emit(PostState.PostError)
        }
    }

    private suspend fun FlowCollector<PostState>.fetchInitialPosts(
        event: PostEvent.Fetch.FetchFromZero
    ) {
        emit(PostState.PostLoading)
        val posts = postService.posts(start = event.start, limit = event.limit)
        val state = PostState.PostLoaded(
            posts = posts,
            hasReachedMax = posts.isEmpty()
        )
        emit(state)
    }

    private suspend fun FlowCollector<PostState>.fetchNewPosts(
        event: PostEvent.Fetch.FetchFromPrevious,
        state: PostState.PostLoaded
    ) {
        if (state.hasReachedMax) return

        emit(PostState.PostLoading)
        val posts = postService.posts(start = event.start, limit = event.limit)
        emit(state.copy(posts = state.posts + posts, hasReachedMax = posts.isEmpty()))
    }
}