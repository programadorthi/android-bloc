package br.com.programadorthi.infinitelist.bloc

sealed class PostEvent {
    sealed class Fetch(val start: Int, val limit: Int) : PostEvent() {
        class FetchFromPrevious(start: Int, limit: Int = PAGE_SIZE) : Fetch(start = start, limit = limit)
        class FetchFromZero(limit: Int = PAGE_SIZE) : Fetch(start = 0, limit = limit)
        companion object {
            const val PAGE_SIZE = 20
        }
    }
}