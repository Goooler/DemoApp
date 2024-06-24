package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.collections.immutable.toImmutableList

/**
 * Created on 2020/10/22.
 *
 * Simple FeAdapter. You can write your own [RecyclerView.Adapter] according to this.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@Suppress("NotifyDataSetChanged", "TooManyFunctions")
abstract class BaseRvAdapter<M : IVhModelType> :
  RecyclerView.Adapter<BindingViewHolder>(),
  IMutableRvAdapter<M> {

  private val helper by lazy(LazyThreadSafetyMode.NONE) { RvAdapterHelper(this) }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    helper.onAttachedToRecyclerView(recyclerView)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    helper.onDetachedFromRecyclerView(recyclerView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder =
    helper.onCreateViewHolder(parent, viewType)

  override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
    helper.onBindViewHolder(holder, position)
  }

  override fun onBindViewHolder(
    holder: BindingViewHolder,
    position: Int,
    payloads: List<Any>,
  ) {
    helper.onBindViewHolder(holder, position, payloads)
  }

  @LayoutRes
  override fun getItemViewType(position: Int): Int =
    helper.list[position].viewType

  override fun getItemCount(): Int = helper.list.size

  override operator fun get(position: Int): M = helper.list[position]

  override var list: List<M>
    get() = helper.list.toImmutableList()
    set(value) {
      helper.list = value
      notifyDataSetChanged()
    }

  override fun refreshItems(items: List<M>) {
    helper.refreshItems(items, ::notifyItemChanged)
  }

  override fun removeItem(index: Int) {
    helper.removeItem(index, ::notifyItemRemoved)
  }

  override fun removeItem(item: M) {
    helper.removeItem(item, ::notifyItemRemoved)
  }
}
