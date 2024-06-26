package io.github.goooler.adapter.rv.paging

import androidx.annotation.LayoutRes
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.goooler.adapter.rv.core.BindingViewHolder
import io.github.goooler.adapter.rv.core.IRvBinding
import io.github.goooler.adapter.rv.diff.DiffCallback
import io.github.goooler.adapter.rv.diff.IDiffVhModelType
import io.github.goooler.adapter.rv.internal.IRvAdapter

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
public abstract class BaseRvPagingAdapter<M : IDiffVhModelType> private constructor(
  callback: DiffCallback<M>,
  private val delegate: IRvAdapter.Impl<M, BaseRvPagingAdapter<M>>,
) : PagingDataAdapter<M, BindingViewHolder>(callback),
  IRvBinding<M>,
  IRvAdapter<M> by delegate {

  public var onLoadStatusListener: OnLoadStatusListener? = null

  public override val list: List<M> get() = snapshot().items

  public constructor(callback: DiffCallback<M> = DiffCallback()) : this(callback, IRvAdapter.Impl()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  public override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    delegate.onAttachedToRecyclerView(recyclerView)
    addLoadStateListener(loadStateListener)
  }

  public override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    delegate.onDetachedFromRecyclerView(recyclerView)
    removeLoadStateListener(loadStateListener)
  }

  @LayoutRes
  public override fun getItemViewType(position: Int): Int = getItem(position)?.viewType ?: 0

  public override operator fun get(position: Int): M? = getItem(position)

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

  public interface OnLoadStatusListener {
    public fun onRefresh() {}
    public fun onLoadMore() {}

    /**
     * Not loading
     */
    public fun onNotLoading()

    /**
     * No more data
     */
    public fun onNoMoreData()

    /**
     * Empty data
     */
    public fun onEmpty()

    /**
     * Error occurred
     */
    public fun onError(t: Throwable)
  }
}
