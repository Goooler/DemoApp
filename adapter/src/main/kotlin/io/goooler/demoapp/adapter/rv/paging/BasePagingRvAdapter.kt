package io.goooler.demoapp.adapter.rv.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IRvAdapter
import io.goooler.demoapp.adapter.rv.core.RvAdapterHelper
import io.goooler.demoapp.adapter.rv.diff.DiffCallBack
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType

/**
 * Created on 2020/10/09.
 *
 * @author Goooler
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BasePagingRvAdapter<M : IDiffVhModelType>(callback: DiffCallBack<M> = DiffCallBack()) :
    PagingDataAdapter<M, BindingViewHolder>(callback), IRvAdapter<M> {

    private val helper by lazy(LazyThreadSafetyMode.NONE) { RvAdapterHelper(this) }

    var onLoadStatusListener: OnLoadStatusListener? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        helper.onAttachedToRecyclerView(recyclerView)
        listenLoadState()
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

    override fun getItemViewType(position: Int): Int = getItem(position)?.viewType ?: 0

    override fun getModel(position: Int): M? = getItem(position)

    private fun listenLoadState() {
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