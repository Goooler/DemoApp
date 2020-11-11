@file:Suppress("UNCHECKED_CAST")

package io.goooler.demoapp.adapter.rv.core

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

interface IRvAdapterMutable<M : IVhModelType> {

    /**
     * Set data list.
     */
    fun setList(list: List<M>)

    /**
     * Get data list.
     */
    fun getList(): List<M>

    /**
     * Refresh some items.
     */
    fun refreshItems(items: List<M>)

    fun setList(vararg items: M) {
        setList(items.toList())
    }

    fun refreshItems(vararg items: M) {
        refreshItems(items.toList())
    }
}

@BindingAdapter(value = ["binding_rv_dataList"], requireAll = true)
fun <M : IVhModelType> RecyclerView.setList(list: List<M>?) {
    (adapter as? IRvAdapterMutable<M>)?.setList(list.orEmpty())
}

@BindingAdapter(value = ["binding_rv_refreshItems"], requireAll = true)
fun <M : IVhModelType> RecyclerView.refreshItems(items: List<M>?) {
    (adapter as? IRvAdapterMutable<M>)?.refreshItems(items.orEmpty())
}