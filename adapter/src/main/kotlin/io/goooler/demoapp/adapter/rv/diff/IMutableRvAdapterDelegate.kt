package io.goooler.demoapp.adapter.rv.diff

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IRvAdapter
import io.goooler.demoapp.adapter.rv.core.ISpanSize
import io.goooler.demoapp.adapter.rv.core.ISpanSize.Companion.SPAN_SIZE_FULL
import io.goooler.demoapp.adapter.rv.core.IVhModelType
import io.goooler.demoapp.adapter.rv.core.IVhModelWrapper
import io.goooler.demoapp.adapter.rv.core.ViewTypeDelegateManager
import kotlinx.collections.immutable.toImmutableList

internal interface IMutableRvAdapterDelegate<M : IVhModelType, VH : BindingViewHolder> : IRvAdapter<M> {

  fun onAttachedToRecyclerView(recyclerView: RecyclerView)

  fun onDetachedFromRecyclerView(recyclerView: RecyclerView)

  fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): VH

  fun onBindViewHolder(holder: BindingViewHolder, @IntRange(from = 0) position: Int)

  fun onBindViewHolder(
    holder: BindingViewHolder,
    @IntRange(from = 0) position: Int,
    payloads: List<Any>,
  )

  fun getItemViewType(@IntRange(from = 0) position: Int): Int

  fun transform(original: List<M>): List<M>

  fun refreshItems(items: List<M>, notify: (Int) -> Unit)

  fun removeItem(index: Int, notify: (Int) -> Unit)

  fun removeItem(item: M, notify: (Int) -> Unit)
}

/**
 * Created on 2020/10/22.
 *
 * FeAdapterHelper. It can be easily used in adapter.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@Suppress("TooManyFunctions")
internal class RvAdapterDelegate<M : IVhModelType> : IMutableRvAdapterDelegate<M, BindingViewHolder> {

  private val ivdManager = ViewTypeDelegateManager<M>()
  private val _list = mutableListOf<M>()

  internal lateinit var adapter: IRvAdapter<M>

  override var list: List<M>
    get() = _list.toImmutableList()
    set(value) {
      _list.clear()
      _list.addAll(transform(value))
    }

  /**
   * Called when RecyclerView starts observing this Adapter.
   */
  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    initManager(ivdManager)
    fixSpanSize(recyclerView)
  }

  /**
   * Called when RecyclerView stops observing this Adapter.
   */
  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    ivdManager.clear()
    recyclerView.adapter = null
  }

  override fun get(position: Int): M = _list[position]

  @LayoutRes
  override fun getItemViewType(position: Int): Int = get(position).viewType

  /**
   * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
   */
  override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
    return createVH(parent, viewType).also {
      adapter.onCreateVHForAll(it.binding)
      ivdManager.onCreateVH(it.binding, viewType)
    }
  }

  override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
    onBindViewHolder(holder, position, emptyList())
  }

  /**
   * Called by RecyclerView to display the data at the specified position.
   */
  override fun onBindViewHolder(
    holder: BindingViewHolder,
    @IntRange(from = 0) position: Int,
    payloads: List<Any>,
  ) {
    get(position).let {
      setFullSpan(holder, it)
      adapter.onBindVHForAll(holder.binding, it, payloads)
      ivdManager.onBindVH(holder.binding, it, payloads)
      holder.binding.executePendingBindings()
    }
  }

  override fun onCreateVHForAll(binding: ViewDataBinding) {
    TODO("Not yet implemented")
  }

  override fun onBindVHForAll(binding: ViewDataBinding, model: M, payloads: List<Any>) {
    TODO("Not yet implemented")
  }

  /**
   * Compare the list to find the same items and refresh them.
   */
  override fun refreshItems(items: List<M>, notify: (Int) -> Unit) {
    transform(items).forEach {
      if (it in _list) {
        notify(_list.indexOf(it))
      }
    }
  }

  override fun removeItem(index: Int, notify: (Int) -> Unit) {
    _list.removeAt(index)
    notify(index)
  }

  override fun removeItem(item: M, notify: (Int) -> Unit) {
    _list.indexOf(item).takeIf { it != -1 }?.let {
      removeItem(it, notify)
    }
  }

  /**
   * Transform data list. Always return a new list.
   */
  override fun transform(original: List<M>): List<M> {
    val result = mutableListOf<M>()
    original.forEach { findLeaf(it, result) }
    return result
  }

  /**
   * Recursively traversing all leaf nodes.
   */
  @Suppress("UNCHECKED_CAST")
  private fun findLeaf(model: M, list: MutableList<M>) {
    if (model is IVhModelWrapper<*>) {
      if (model.viewType != -1) list += model
      model.subList.forEach { findLeaf(it as M, list) }
    } else {
      list += model
    }
  }

  /**
   * Fix span size when recyclerView's layoutManager is [GridLayoutManager].
   */
  private fun fixSpanSize(recyclerView: RecyclerView) {
    (recyclerView.layoutManager as? GridLayoutManager)?.let {
      it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
          (get(position) as? ISpanSize)?.spanSize?.let { size ->
            return if (size == SPAN_SIZE_FULL) it.spanCount else size
          }
          return it.spanCount
        }
      }
    }
  }

  /**
   * Set full span when recyclerView's layoutManager is [StaggeredGridLayoutManager].
   */
  private fun setFullSpan(holder: RecyclerView.ViewHolder, item: M) {
    (holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams)?.let {
      it.isFullSpan = (item as? ISpanSize)?.spanSize == SPAN_SIZE_FULL
    }
  }
}
