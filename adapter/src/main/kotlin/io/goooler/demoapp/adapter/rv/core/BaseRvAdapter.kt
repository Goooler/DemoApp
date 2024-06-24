package io.goooler.demoapp.adapter.rv.core

import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2020/10/22.
 *
 * Simple FeAdapter. You can write your own [RecyclerView.Adapter] according to this.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@Suppress("TooManyFunctions")
abstract class BaseRvAdapter<M : IVhModelType> private constructor(
  private val delegate: RvAdapterDelegate<M>,
) : RecyclerView.Adapter<BindingViewHolder>(),
  IMutableRvAdapter<M>,
  IRvAdapterDelegate<M, BindingViewHolder> by delegate {

  constructor() : this(RvAdapterDelegate()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  override var list: List<M>
    get() = delegate.list
    set(value) {
      delegate.list = value
      @Suppress("NotifyDataSetChanged")
      notifyDataSetChanged()
    }

  override fun refreshItems(items: List<M>) {
    delegate.refreshItems(items, ::notifyItemChanged)
  }

  override fun removeItem(index: Int) {
    delegate.removeItem(index, ::notifyItemRemoved)
  }

  override fun removeItem(item: M) {
    delegate.removeItem(item, ::notifyItemRemoved)
  }
}
