package io.goooler.demoapp.adapter.rv.diff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.base.BaseRvAdapter
import io.goooler.demoapp.adapter.rv.base.ViewTypeDelegateManager
import io.goooler.demoapp.adapter.rv.datasource.BasePagingSource

/**
 * Created on 2020/10/09.
 *
 * @author Goooler
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BasePagingAdapter<T : IModelDiff<T>>(
    diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isItemTheSame(newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isContentTheSame(newItem)
    }
) : PagingDataAdapter<T, BaseRvAdapter.BaseViewHolder>(diffCallback) {

    private val ivdManager by lazy(LazyThreadSafetyMode.NONE) {
        ViewTypeDelegateManager<T>()
    }

    var onLoadStatusListener: OnLoadStatusListener? = null

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
        addLoadStateListener {
            when {
                it.refresh is LoadState.Loading -> onLoadStatusListener?.onRefresh()
                it.append is LoadState.Loading -> onLoadStatusListener?.onLoadMore()
                else -> {
                    onLoadStatusListener?.onNotLoading()
                    if (it.refresh is LoadState.Error) {
                        when (val throwable = (it.refresh as LoadState.Error).error) {
                            is BasePagingSource.EmptyDataException -> onLoadStatusListener?.onEmpty()
                            else -> onLoadStatusListener?.onError(throwable)
                        }
                    }
                    if (it.append is LoadState.Error) {
                        when (val throwable = (it.append as LoadState.Error).error) {
                            is BasePagingSource.NoMoreDataException -> onLoadStatusListener?.onNoMoreData()
                            else -> onLoadStatusListener?.onError(throwable)
                        }
                    }
                }
            }
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

    interface OnLoadStatusListener {
        fun onRefresh() {}
        fun onLoadMore() {}

        /**
         * 没有在下拉刷新或上拉加载
         */
        fun onNotLoading()

        /**
         * 没有更多数据
         */
        fun onNoMoreData()

        /**
         * 第一页请求为空
         */
        fun onEmpty()

        /**
         * 暂时没有区分第一页加载失败或是中间页加载失败
         */
        fun onError(t: Throwable)
    }
}