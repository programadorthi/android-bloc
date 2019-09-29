package br.com.programadorthi.infinitelist.presentation.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PostScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int,
    private val postLoadMore: PostLoadMore
) : RecyclerView.OnScrollListener() {

    private var isLastPage = false
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (isLoading || isLastPage) return

        if ((visibleItemCount + firstVisibleItemPosition) < totalItemCount) return

        if (firstVisibleItemPosition < 0) return

        if (totalItemCount < pageSize) return

        isLoading = true
        postLoadMore.loadMore()
    }

    fun loading(loading: Boolean) {
        isLoading = loading
    }

    fun reachedLastPage(lastPage: Boolean) {
        isLastPage = lastPage
    }
}
