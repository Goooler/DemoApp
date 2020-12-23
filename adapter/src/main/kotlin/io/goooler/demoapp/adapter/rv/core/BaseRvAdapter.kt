package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

/**
 * Created on 2020/10/22.
 *
 * Simple FeAdapter. You can write your own [RecyclerView.Adapter] according to this.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 * @see RecyclerView.Adapter
 * @see RvAdapterHelper
 * @see IRvAdapter
 */
abstract class BaseRvAdapter<M : IVhModelType> :
  RecyclerView.Adapter<BindingViewHolder>(),
  IRvAdapter<M>,
  IRvAdapterMutable<M> {

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

  override fun onBindViewHolder(holder: BindingViewHolder, @IntRange(from = 0) position: Int) {
    helper.onBindViewHolder(holder, position)
  }

  @LayoutRes
  override fun getItemViewType(@IntRange(from = 0) position: Int): Int =
    helper.list[position].viewType

  override fun getItemCount(): Int = helper.list.size

  override fun getModel(@IntRange(from = 0) position: Int): M? = helper.list[position]

  override var list: List<M>
    get() = Collections.unmodifiableList(helper.list)
    set(value) {
      helper.list = value
      notifyDataSetChanged()
    }

  override fun refreshItems(items: List<M>) {
    helper.refreshItems(items) {
      if (it in 0 until itemCount) {
        notifyItemChanged(it)
      }
    }
  }

  override fun removeItem(index: Int) {
    helper.removeItem(index)
    notifyItemRemoved(index)
  }

  override fun removeItem(item: M) {
    helper.removeItem(item) {
      notifyItemRemoved(it)
    }
  }
}
