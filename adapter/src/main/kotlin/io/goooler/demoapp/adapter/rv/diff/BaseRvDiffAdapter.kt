package io.goooler.demoapp.adapter.rv.diff

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IRvAdapter
import io.goooler.demoapp.adapter.rv.core.IRvAdapterMutable
import io.goooler.demoapp.adapter.rv.core.RvAdapterHelper

/**
 * Created on 2020/10/22.
 *
 * DiffListAdapter. This adapter used {@link DiffUtil}.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 * @see ListAdapter
 * @see DiffUtil
 * @see RvAdapterHelper
 * @see IRvAdapter
 */
abstract class BaseRvDiffAdapter<M : IDiffVhModelType>(callback: DiffCallBack<M> = DiffCallBack()) :
    ListAdapter<M, BindingViewHolder>(callback), IRvAdapter<M>, IRvAdapterMutable<M> {

    private val helper by lazy(LazyThreadSafetyMode.NONE) { RvAdapterHelper(this) }

    private val dataList = ArrayList<M>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        helper.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        helper.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return helper.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        helper.onBindViewHolder(holder, position)
    }

    override fun getModel(position: Int): M? = getItem(position)

    override fun getItemViewType(position: Int): Int = getItem(position)?.viewType ?: 0

    override fun setList(list: List<M>) {
        dataList.clear()
        helper.transform(list).let {
            dataList.addAll(it)
            // Always submit a new list.
            submitList(it)
        }
    }

    /**
     * Just for read.
     */
    override fun getList(): List<M> = dataList

    /**
     * Please do not use it with setList()!
     */
    override fun refreshItems(items: List<M>) {
        helper.refreshItems(items, dataList) {
            if (it in 0 until itemCount) {
                notifyItemChanged(it)
            }
        }
    }
}