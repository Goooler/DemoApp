package io.goooler.demoapp.adapter.rv.diff

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IMutableRvAdapter
import io.goooler.demoapp.adapter.rv.core.IRvAdapterDelegate
import io.goooler.demoapp.adapter.rv.core.IRvBinding
import io.goooler.demoapp.adapter.rv.core.RvAdapterDelegate

/**
 * Created on 2020/10/22.
 *
 * DiffListAdapter. This adapter used [DiffUtil].
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BaseRvDiffAdapter<M : IDiffVhModelType> private constructor(
  callback: AsyncDifferConfig<M>,
  private val delegate: RvAdapterDelegate<M>,
) : ListAdapter<M, BindingViewHolder>(callback),
  IRvBinding<M>,
  IMutableRvAdapter<M>,
  IRvAdapterDelegate<M, BindingViewHolder> by delegate {

  constructor(callback: DiffCallBack<M> = DiffCallBack()) : this(
    AsyncDifferConfig.Builder(callback).build(),
    RvAdapterDelegate(),
  ) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  constructor(config: AsyncDifferConfig<M>) : this(config, RvAdapterDelegate()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  override var list: List<M>
    get() = delegate.list
    set(value) {
      delegate.list = value
      submitList(delegate.transform(value))
    }

  override fun onCreateVHForAll(binding: ViewDataBinding) {
    onCreateVH(binding)
  }

  override fun onBindVHForAll(binding: ViewDataBinding, model: M, payloads: List<Any>) {
    onBindVH(binding, model, payloads)
  }

  /**
   * Please do not use it with setList() !
   */
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
