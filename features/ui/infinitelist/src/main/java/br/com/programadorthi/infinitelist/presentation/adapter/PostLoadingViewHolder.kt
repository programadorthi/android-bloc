package br.com.programadorthi.infinitelist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.programadorthi.infinitelist.R

class PostLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        @JvmStatic
        fun create(parent: ViewGroup): PostLoadingViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val layout = inflater.inflate(R.layout.item_loading, parent, false)
            return PostLoadingViewHolder(layout)
        }
    }
}
