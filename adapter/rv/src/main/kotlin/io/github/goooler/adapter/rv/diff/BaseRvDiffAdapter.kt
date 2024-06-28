package io.github.goooler.adapter.rv.diff

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.github.goooler.adapter.rv.core.BindingViewHolder
import io.github.goooler.adapter.rv.core.IRvBinding
import io.github.goooler.adapter.rv.internal.IMutableRvAdapter

/**
 * DiffListAdapter. This adapter used [DiffUtil].
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
public abstract class BaseRvDiffAdapter<M : IDiffVhModelType> private constructor(
  asyncDifferConfig: AsyncDifferConfig<M>,
  private val delegate: IMutableRvAdapter.Impl<M, BaseRvDiffAdapter<M>>,
) : ListAdapter<M, BindingViewHolder>(asyncDifferConfig),
  IRvBinding<M>,
  IMutableRvAdapter<M> by delegate {

  public constructor(callback: DiffCallback<M> = DiffCallback()) : this(
    AsyncDifferConfig.Builder(callback).build(),
    IMutableRvAdapter.Impl(),
  ) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  public constructor(config: AsyncDifferConfig<M>) : this(config, IMutableRvAdapter.Impl()) {
    @Suppress("LeakingThis")
    delegate.adapter = this
  }

  public override var list: List<M>
    get() = delegate.list
    set(value) {
      delegate.list = value
      submitList(delegate.list)
    }
}
