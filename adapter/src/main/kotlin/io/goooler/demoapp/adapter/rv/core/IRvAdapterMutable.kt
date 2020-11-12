@file:Suppress("UNCHECKED_CAST")

package io.goooler.demoapp.adapter.rv.core

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

interface IRvAdapterMutable<M : IVhModelType> {

    /**
     * Set or Get data list.
     */
    var list: List<M>

    /**
     * Refresh some items.
     */
    fun refreshItems(items: List<M>)

    fun removeItem(index: Int)

    fun removeItem(item: M)
}

@BindingAdapter(value = ["binding_rv_dataList"], requireAll = true)
fun <M : IVhModelType> RecyclerView.setList(list: List<M>?) {
    (adapter as? IRvAdapterMutable<M>)?.list = list.orEmpty()
}

@BindingAdapter(value = ["binding_rv_refreshItems"], requireAll = true)
fun <M : IVhModelType> RecyclerView.refreshItems(items: List<M>?) {
    (adapter as? IRvAdapterMutable<M>)?.refreshItems(items.orEmpty())
}