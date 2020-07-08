package io.goooler.demoapp.adapter.diff

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import io.goooler.demoapp.adapter.base.BaseAdapter

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.1.0
 * @since 1.1.0
 */
abstract class BaseDiffAdapter<T : IModelDiff<T>> : BaseAdapter<T>() {

    private val mDiffer: AsyncListDiffer<T> by lazy {
        AsyncListDiffer(this, diffCallBack())
    }

    override fun list(): List<T> {
        return mDiffer.currentList
    }

    fun submitList(list: List<T>) {
        mDiffer.submitList(list.multiList())
    }

    fun differ(): AsyncListDiffer<T> {
        return mDiffer
    }

    protected open fun diffCallBack(): DiffUtil.ItemCallback<T> {
        return diffCallBack
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<T>() {
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areItemsTheSame(newItem)
        }

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return null
        }
    }
}