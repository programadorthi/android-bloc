package br.com.programadorthi.infinitelist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.infinitelist.R
import br.com.programadorthi.infinitelist.bloc.PostEvent
import br.com.programadorthi.infinitelist.bloc.PostState
import br.com.programadorthi.infinitelist.di.POST_BLOC
import br.com.programadorthi.infinitelist.presentation.adapter.PostAdapter
import br.com.programadorthi.infinitelist.presentation.adapter.PostLoadMore
import br.com.programadorthi.infinitelist.presentation.adapter.PostScrollListener
import kotlinx.android.synthetic.main.activity_post_list.postRecyclerView
import kotlinx.android.synthetic.main.activity_post_list.swipeRefresh
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PostListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    PostLoadMore {

    private val postBloc: Bloc<PostEvent, PostState> by viewModel(named(POST_BLOC))

    private val adapter = PostAdapter()

    private lateinit var postScrollListener: PostScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        setupRecyclerView()

        lifecycleScope.launch {
            postBloc.state.collect { state -> handleState(state) }
        }
    }

    override fun onRefresh() {
        postBloc.dispatch(PostEvent.Fetch.FetchFromZero())
        adapter.clearAll()
    }

    override fun loadMore() {
        val state = postBloc.currentState as? PostState.PostLoaded ?: return

        postBloc.dispatch(PostEvent.Fetch.FetchFromPrevious(start = state.posts.size))
        postScrollListener.loading(true)
        postRecyclerView.post {
            adapter.loading(true)
        }
    }

    private fun setupRecyclerView() {
        postRecyclerView.adapter = adapter

        postScrollListener = PostScrollListener(
            layoutManager = postRecyclerView.layoutManager as LinearLayoutManager,
            pageSize = PostEvent.Fetch.PAGE_SIZE,
            postLoadMore = this
        )

        postRecyclerView.addOnScrollListener(postScrollListener)
        swipeRefresh.setOnRefreshListener(this)
    }

    private fun handleState(state: PostState) {
        when (state) {
            is PostState.PostLoaded -> {
                postScrollListener.reachedLastPage(state.hasReachedMax)
                if (!state.hasReachedMax) {
                    adapter.updateDataSet(state.posts)
                }
            }
            is PostState.PostError -> {
                Toast.makeText(this, "There is an error", Toast.LENGTH_SHORT).show()
            }
            is PostState.PostUninitialized -> {
                postBloc.dispatch(PostEvent.Fetch.FetchFromZero())
            }
        }

        swipeRefresh.isRefreshing = state is PostState.PostLoading && adapter.isEmpty()

        postScrollListener.loading(state is PostState.PostLoading || swipeRefresh.isRefreshing)

        adapter.loading(state is PostState.PostLoading && !swipeRefresh.isRefreshing)
    }
}