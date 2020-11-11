package io.goooler.demoapp.adapter.rv.core

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
}