package br.com.programadorthi.infinitelist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.programadorthi.infinitelist.R
import br.com.programadorthi.infinitelist.data.Post
import kotlinx.android.synthetic.main.item_post.view.postIdTextView
import kotlinx.android.synthetic.main.item_post.view.postSubtitleTextView
import kotlinx.android.synthetic.main.item_post.view.postTitleTextView

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(post: Post) {
        itemView.postIdTextView.text = post.id.toString()
        itemView.postTitleTextView.text = post.title
        itemView.postSubtitleTextView.text = post.body
    }

    companion object {
        @JvmStatic
        fun create(parent: ViewGroup): PostViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val layout = inflater.inflate(R.layout.item_post, parent, false)
            return PostViewHolder(layout)
        }
    }
}