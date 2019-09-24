package br.com.programadorthi.infinitelist.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.programadorthi.infinitelist.data.Post

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val posts = mutableListOf<Post>()

    private var loadIndex = -1

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        if (loadIndex >= 0 && position == posts.lastIndex) {
            return VIEW_TYPE_LOADING
        }
        return VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> PostLoadingViewHolder.create(parent)
            VIEW_TYPE_NORMAL -> PostViewHolder.create(parent)
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            val data = posts[position]
            holder.bind(data)
        }
    }

    fun isEmpty(): Boolean = posts.isEmpty()

    fun clearAll() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun loading(show: Boolean) {
        if (show && loadIndex < 0) {
            posts.add(Post.LOADING)
            loadIndex = posts.lastIndex
            notifyItemInserted(loadIndex)
            return
        }

        if (!show && loadIndex >= 0) {
            val item = posts[loadIndex]
            if (item == Post.LOADING) {
                posts.removeAt(loadIndex)
                notifyItemRemoved(loadIndex)
            }
            loadIndex = -1
        }
    }

    fun updateDataSet(posts: List<Post>) {
        with(this.posts) {
            val firstIndex = size
            val itemCount = posts.size - size
            clear()
            addAll(posts)
            notifyItemRangeInserted(firstIndex, itemCount)
        }
    }

    private companion object {
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_NORMAL = 2
    }
}