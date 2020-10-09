package io.goooler.demoapp.main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.base.util.image.load
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.model.MainListItemModel

class MainListPagingAdapter(val listener: MainListAdapter.OnEventListener) :
    PagedListAdapter<MainListItemModel, MainListPagingAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<MainListItemModel>() {
            override fun areItemsTheSame(
                oldItem: MainListItemModel,
                newItem: MainListItemModel
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: MainListItemModel,
                newItem: MainListItemModel
            ): Boolean = oldItem.content == newItem.content
        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.tvContent.text = it.content
            holder.ivLogo.load(it.logoUrl)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        val ivLogo: ImageView = itemView.findViewById(R.id.iv_logo)
    }
}