@file:Suppress("unused")

package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import io.goooler.demoapp.adapter.rv.base.BaseRvAdapter

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.1.0
 * @since 1.1.0
 */
abstract class BaseDiffAdapter<T : IModelDiff<T>> : BaseRvAdapter<T>() {

    private val differ by lazy(LazyThreadSafetyMode.NONE) {
        AsyncListDiffer(this, diffCallback())
    }

    override val modelList: MutableList<T> = differ.currentList

    fun submitList(list: List<T>) {
        differ.submitList(list.toMultiList())
    }

    protected open fun diffCallback(): DiffUtil.ItemCallback<T> = diffCallback

    private val diffCallback = object : DiffUtil.ItemCallback<T>() {

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isContentTheSame(newItem)

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isItemTheSame(newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? = null
    }
}