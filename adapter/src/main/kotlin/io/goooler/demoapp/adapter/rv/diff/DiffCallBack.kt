package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.DiffUtil

open class DiffCallBack<N : IDiffVhModelType> : DiffUtil.ItemCallback<N>() {

    /**
     * Judge whether the contents of two items are the same.
     */
    override fun areContentsTheSame(oldItem: N, newItem: N): Boolean =
        oldItem.isContentTheSame(newItem)

    /**
     * Judge whether two items use the same item.
     */
    override fun areItemsTheSame(oldItem: N, newItem: N): Boolean = oldItem.isItemTheSame(newItem)

    /**
     * Advanced usage, used to extract changes and refresh local variables accurately.
     * Not for the time being.
     */
    override fun getChangePayload(oldItem: N, newItem: N): Any? = null
}