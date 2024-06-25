package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IMutableRvAdapter
import io.goooler.demoapp.adapter.rv.core.IRvAdapterDelegate
import io.goooler.demoapp.adapter.rv.core.IRvBinding
import kotlinx.collections.immutable.toImmutableList

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
  asyncDifferConfig: AsyncDifferConfig<M>,
  private val delegate: IRvAdapterDelegate.Impl<M, BaseRvDiffAdapter<M>>,
) : ListAdapter<M, BindingViewHolder>(asyncDifferConfig),
  IRvBinding<M>,
  IMutableRvAdapter<M>,
  IRvAdapterDelegate<M, BindingViewHolder> by delegate {

  constructor(callback: DiffCallback<M> = DiffCallback()) : this(
    AsyncDifferConfig.Builder(callback).build(),
    IRvAdapterDelegate.Impl(),
  ) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  constructor(config: AsyncDifferConfig<M>) : this(config, IRvAdapterDelegate.Impl()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  override var list: List<M>
    get() = delegate.list.toImmutableList()
    set(value) {
      delegate.list = value
      submitList(delegate.transform(value))
    }
}
