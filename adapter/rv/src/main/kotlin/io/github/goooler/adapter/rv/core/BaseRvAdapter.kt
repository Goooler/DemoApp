package io.github.goooler.adapter.rv.core

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
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class BaseRvAdapter<M : IVhModelType> private constructor(
  private val delegate: IMutableRvAdapter.Impl<M, BaseRvAdapter<M>>,
) : RecyclerView.Adapter<BindingViewHolder>(),
  IRvBinding<M>,
  IMutableRvAdapter<M> by delegate {

  constructor() : this(IMutableRvAdapter.Impl()) {
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

  override fun getItemCount(): Int = delegate.list.size
}
