package io.goooler.demoapp.adapter.rv.paging

import androidx.annotation.LayoutRes
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IRvAdapter
import io.goooler.demoapp.adapter.rv.core.IRvAdapterDelegate
import io.goooler.demoapp.adapter.rv.core.IRvBinding
import io.goooler.demoapp.adapter.rv.core.RvAdapterDelegate
import io.goooler.demoapp.adapter.rv.diff.DiffCallBack
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType

/**
 * Created on 2020/10/09.
 *
 * @author Goooler
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BaseRvPagingAdapter<M : IDiffVhModelType> private constructor(
  callback: DiffCallBack<M>,
  private val delegate: RvAdapterDelegate<M, BaseRvPagingAdapter<M>>,
) : PagingDataAdapter<M, BindingViewHolder>(callback),
  IRvBinding<M>,
  IRvAdapter<M>,
  IRvAdapterDelegate<M, BindingViewHolder> by delegate {

  var onLoadStatusListener: OnLoadStatusListener? = null

  override val list: List<M> get() = snapshot().items

  constructor(callback: DiffCallBack<M> = DiffCallBack()) : this(callback, RvAdapterDelegate()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    delegate.onAttachedToRecyclerView(recyclerView)
    addLoadStateListener(loadStateListener)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    delegate.onDetachedFromRecyclerView(recyclerView)
    removeLoadStateListener(loadStateListener)
  }

  @LayoutRes
  override fun getItemViewType(position: Int): Int =
    getItem(position)?.viewType ?: 0

  override operator fun get(position: Int): M? = getItem(position)

  private val loadStateListener: (CombinedLoadStates) -> Unit = {
    when {
      it.refresh is LoadState.Loading -> onLoadStatusListener?.onRefresh()
      it.append is LoadState.Loading -> onLoadStatusListener?.onLoadMore()
      else -> {
        onLoadStatusListener?.onNotLoading()
        if (it.refresh is LoadState.Error) {
          when (val throwable = (it.refresh as LoadState.Error).error) {
            is PagingSourceException.EmptyDataException -> onLoadStatusListener?.onEmpty()
            else -> onLoadStatusListener?.onError(throwable)
          }
        }
        if (it.append is LoadState.Error) {
          when (val throwable = (it.append as LoadState.Error).error) {
            is PagingSourceException.NoMoreDataException -> onLoadStatusListener?.onNoMoreData()
            else -> onLoadStatusListener?.onError(throwable)
          }
        }
      }
    }
  }

  interface OnLoadStatusListener {
    fun onRefresh() {}
    fun onLoadMore() {}

    /**
     * Not loading
     */
    fun onNotLoading()

    /**
     * No more data
     */
    fun onNoMoreData()

    /**
     * Empty data
     */
    fun onEmpty()

    /**
     * Error occurred
     */
    fun onError(t: Throwable)
  }
}
