package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.goooler.demoapp.adapter.rv.core.ISpanSize.Companion.SPAN_SIZE_FULL

/**
 * Keep the same signature as [RecyclerView.Adapter].
 *
 * Workaround for [KT-21955](https://youtrack.jetbrains.com/issue/KT-21955).
 */
internal interface RecyclerViewAdapter<VH : RecyclerView.ViewHolder> {
  fun onAttachedToRecyclerView(recyclerView: RecyclerView)
  fun onDetachedFromRecyclerView(recyclerView: RecyclerView)
  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
  fun onBindViewHolder(holder: BindingViewHolder, position: Int)
  fun onBindViewHolder(holder: BindingViewHolder, position: Int, payloads: List<Any>)
  fun getItemViewType(position: Int): Int
}

internal interface IRvAdapterDelegate<M : IVhModelType, VH : BindingViewHolder> :
  IRvAdapter<M>,
  RecyclerViewAdapter<VH> {

  @Suppress("TooManyFunctions")
  class Impl<M : IVhModelType, AP> : IRvAdapterDelegate<M, BindingViewHolder>
    where AP : IRvAdapter<M>,
          AP : IRvBinding<M> {

    private val ivdManager = ViewTypeDelegateManager<M>()
    private val _list = mutableListOf<M>()

    lateinit var adapter: AP

    override var list: List<M>
      get() = _list
      set(value) {
        _list.clear()
        _list.addAll(transform(value))
      }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
      initManager(ivdManager)
      fixSpanSize(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      ivdManager.clear()
      recyclerView.adapter = null
    }

    override fun get(position: Int): M = _list.getOrElse(position) {
      adapter[position] ?: error("No such a element in $position in $adapter")
    }

    @LayoutRes
    override fun getItemViewType(position: Int): Int = get(position).viewType

    override fun onCreateViewHolder(
      parent: ViewGroup,
      @LayoutRes viewType: Int,
    ): BindingViewHolder {
      return BindingViewHolder.create(parent, viewType).also {
        adapter.onCreateVHForAll(it.binding)
        ivdManager.onCreateVH(it.binding, viewType)
      }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
      onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int, payloads: List<Any>) {
      get(position).let {
        setFullSpan(holder, it)
        adapter.onBindVHForAll(holder.binding, it, payloads)
        ivdManager.onBindVH(holder.binding, it, payloads)
        holder.binding.executePendingBindings()
      }
    }

    override fun onCreateVHForAll(binding: ViewDataBinding) {
      adapter.onCreateVH(binding)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: M, payloads: List<Any>) {
      adapter.onBindVH(binding, model, payloads)
    }

    /**
     * Compare the list to find the same items and refresh them.
     */
    fun refreshItems(items: List<M>, notify: (Int) -> Unit) {
      transform(items).forEach {
        if (it in _list) {
          notify(_list.indexOf(it))
        }
      }
    }

    fun removeItem(index: Int, notify: (Int) -> Unit) {
      _list.removeAt(index)
      notify(index)
    }

    fun removeItem(item: M, notify: (Int) -> Unit) {
      _list.indexOf(item).takeIf { it != -1 }?.let {
        removeItem(it, notify)
      }
    }

    /**
     * Transform data list. Always return a new list.
     */
    fun transform(original: List<M>): List<M> {
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
}
