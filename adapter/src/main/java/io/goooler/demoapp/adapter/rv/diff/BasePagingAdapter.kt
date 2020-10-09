package io.goooler.demoapp.adapter.rv.diff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.base.BaseRvAdapter
import io.goooler.demoapp.adapter.rv.base.ViewTypeDelegateManager

/**
 * Created on 2020/10/09.
 *
 * @author Goooler
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BasePagingAdapter<T : IModelDiff<T>> :
    PagedListAdapter<T, BaseRvAdapter.BaseViewHolder>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isItemTheSame(newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isContentTheSame(newItem)
    }) {

    private val ivdManager by lazy(LazyThreadSafetyMode.NONE) {
        ViewTypeDelegateManager<T>()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        @LayoutRes viewType: Int
    ): BaseRvAdapter.BaseViewHolder {
        val holder = createVH(parent, viewType)
        onCreateVHForAll(holder.binding)
        ivdManager.onCreateVH(holder.binding, viewType)
        return holder
    }

    override fun onBindViewHolder(holder: BaseRvAdapter.BaseViewHolder, position: Int) {
        getItem(position)?.let {
            onBindVHForAll(holder.binding, it)
            ivdManager.onBindVH(holder.binding, it)
            holder.binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.viewType ?: 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.layoutManager as? GridLayoutManager)?.spanSizeLookup = object :
            GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = getItem(position)?.spanSize ?: 1
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        ivdManager.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    abstract fun onCreateVHForAll(binding: ViewDataBinding)

    abstract fun onBindVHForAll(binding: ViewDataBinding, model: T)

    protected open fun createVH(
        parent: ViewGroup,
        @LayoutRes viewType: Int
    ): BaseRvAdapter.BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
        return BaseRvAdapter.BaseViewHolder(binding)
    }
}