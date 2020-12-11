package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.goooler.demoapp.adapter.rv.core.ISpanSize.Companion.SPAN_SIZE_FULL
import io.goooler.demoapp.adapter.rv.core.ISpanSize.Companion.SPAN_SIZE_SINGLE

/**
 * Created on 2020/10/22.
 *
 * FeAdapterHelper. It can be easily used in adapter.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 * @see IRvAdapter
 * @see BaseRvAdapter
 */
internal class RvAdapterHelper<M : IVhModelType>(private val adapter: IRvAdapter<M>) {

  private val ivdManager = ViewTypeDelegateManager<M>()

  private val dataList = ArrayList<M>()

  var list: List<M>
    get() = dataList
    set(value) {
      dataList.run {
        clear()
        addAll(transform(value))
      }
    }

  /**
   * Called when RecyclerView starts observing this Adapter.
   */
  fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    adapter.initManager(ivdManager)
    fixSpanSize(recyclerView)
  }

  /**
   * Called when RecyclerView stops observing this Adapter.
   */
  fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    ivdManager.clear()
    recyclerView.adapter = null
  }

  /**
   * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
   */
  fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
    return adapter.createVH(parent, viewType).also {
      adapter.onCreateVHForAll(it.binding)
      ivdManager.onCreateVH(it.binding, viewType)
    }
  }

  /**
   * Called by RecyclerView to display the data at the specified position.
   */
  fun onBindViewHolder(holder: BindingViewHolder, @IntRange(from = 0) position: Int) {
    adapter.getModel(position)?.let {
      setFullSpan(holder, it)
      adapter.onBindVHForAll(holder.binding, it)
      ivdManager.onBindVH(holder.binding, it)
      holder.binding.executePendingBindings()
    }
  }

  /**
   * Compare the list to find the same items and refresh them.
   */
  fun refreshItems(items: List<M>, notify: (Int) -> Unit) {
    transform(items).forEach {
      if (it in dataList) {
        notify(dataList.indexOf(it))
      }
    }
  }

  /**
   * Transform data list. Always return a new list.
   */
  fun transform(original: List<M>): List<M> {
    val result = ArrayList<M>()
    original.forEach { findLeaf(it, result) }
    return result
  }

  fun removeItem(index: Int) {
    dataList.removeAt(index)
  }

  fun removeItem(item: M, notify: (Int) -> Unit) {
    dataList.indexOf(item).let {
      if (it != -1) {
        removeItem(it)
        notify(it)
      }
    }
  }

  /**
   * Recursively traversing all leaf nodes.
   */
  @Suppress("UNCHECKED_CAST")
  private fun findLeaf(model: M, list: ArrayList<M>) {
    if (model is IVhModelWrapper<*>) {
      model.asList().forEach { findLeaf(it as M, list) }
    } else {
      list.add(model)
    }
  }

  /**
   * Fix span size when recyclerView's layoutManager is [GridLayoutManager].
   */
  private fun fixSpanSize(recyclerView: RecyclerView) {
    (recyclerView.layoutManager as? GridLayoutManager)?.let {
      it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
          (adapter.getModel(position) as? ISpanSize)?.spanSize?.let { size ->
            return if (size == SPAN_SIZE_FULL) it.spanCount else size
          }
          return SPAN_SIZE_SINGLE
        }
      }
    }
  }

  /**
   * Set full span when recyclerView's layoutManager is [StaggeredGridLayoutManager].
   */
  private fun setFullSpan(holder: RecyclerView.ViewHolder, item: M) {
    (holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams)?.let {
      it.isFullSpan = item is ISpanSize && item.spanSize == SPAN_SIZE_FULL
    }
  }
}
