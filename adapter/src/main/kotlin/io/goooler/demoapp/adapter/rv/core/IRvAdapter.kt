package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.goooler.demoapp.adapter.rv.core.ISpanSize.Companion.SPAN_SIZE_FULL
import kotlinx.collections.immutable.toImmutableList

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

/**
 * Created on 2020/10/21.
 *
 * Please let your [RecyclerView.Adapter] implements IExtAdapter.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
internal interface IRvAdapter<M : IVhModelType> : RecyclerViewAdapter<BindingViewHolder> {

  /**
   * Get data list.
   */
  val list: List<M>

  /**
   * Get item by position.
   */
  operator fun get(position: Int): M?

  /**
   * What to do when creating the viewHolder for all.
   */
  fun onCreateVHForAll(binding: ViewDataBinding)

  /**
   * What to do when binding the viewHolder for all.
   */
  fun onBindVHForAll(binding: ViewDataBinding, model: M, payloads: List<Any>)

  /**
   * Init ViewTypeDelegateManager. You can add VTDs.
   */
  fun initManager(manager: ViewTypeDelegateManager<M>) {}

  @Suppress("TooManyFunctions")
  open class Impl<M : IVhModelType, AP> : IRvAdapter<M>
    where AP : IRvAdapter<M>,
          AP : IRvBinding<M>,
          AP : RecyclerView.Adapter<BindingViewHolder> {

    private val ivdManager = ViewTypeDelegateManager<M>()

    @Suppress("PropertyName", "VariableNaming", "ktlint:standard:backing-property-naming")
    protected val _list = mutableListOf<M>()

    lateinit var adapter: AP

    override val list: List<M> get() = _list.toImmutableList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
      initManager(ivdManager)
      fixSpanSize(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
      ivdManager.clear()
      recyclerView.adapter = null
    }

    override operator fun get(position: Int): M = _list.getOrElse(position) {
      // Override get in adapters as a fallback.
      adapter[position] ?: error("No such a element in position $position in adapter $adapter.")
    }

    @LayoutRes
    override fun getItemViewType(position: Int): Int = get(position).viewType

    override fun onCreateViewHolder(
      parent: ViewGroup,
      @LayoutRes viewType: Int,
    ): BindingViewHolder = BindingViewHolder.create(parent, viewType).also {
      adapter.onCreateVHForAll(it.binding)
      ivdManager.onCreateVH(it.binding, viewType)
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

internal interface IMutableRvAdapter<M : IVhModelType> : IRvAdapter<M> {

  /**
   * Set or Get data list.
   */
  override var list: List<M>

  /**
   * Refresh some items.
   *
   * @param items the items to be refreshed, [Int] is for the index of [M].
   */
  fun refreshItems(vararg items: Triple<Int, M, Any?>)

  /**
   * Add some items.
   *
   * @param items the items to be added, [Int] is for the index of [M].
   */
  fun addItems(vararg items: Pair<Int, M>)

  fun removeItem(index: Int)

  fun removeItem(item: M)

  class Impl<M : IVhModelType, AP> :
    IRvAdapter.Impl<M, AP>(),
    IMutableRvAdapter<M>
    where AP : IRvAdapter<M>,
          AP : IRvBinding<M>,
          AP : RecyclerView.Adapter<BindingViewHolder> {

    override var list: List<M>
      get() = super.list
      set(value) {
        _list.clear()
        _list.addAll(flat(value))
      }

    override fun refreshItems(vararg items: Triple<Int, M, Any?>) {
      items.forEach { (index, item, payload) ->
        check(index in _list.indices) { "Index $index out of bounds for length ${_list.size}" }
        _list[index] = item
        adapter.notifyItemChanged(index, payload)
      }
    }

    override fun addItems(vararg items: Pair<Int, M>) {
      items.forEach { (index, item) ->
        check(index in _list.indices) { "Index $index out of bounds for length ${_list.size}" }
        _list.add(index, item)
        adapter.notifyItemInserted(index)
      }
    }

    override fun removeItem(index: Int) {
      _list.removeAt(index)
      adapter::notifyItemRemoved
    }

    override fun removeItem(item: M) {
      _list.indexOf(item).takeIf { it != -1 }?.let {
        removeItem(it)
        adapter::notifyItemRemoved
      }
    }

    private fun flat(original: List<M>): List<M> {
      val result = mutableListOf<M>()
      original.forEach { findLeaf(it, result) }
      return result
    }

    private fun findLeaf(model: M, list: MutableList<M>) {
      if (model is IVhModelWrapper<*>) {
        if (model.viewType != -1) list += model
        @Suppress("UNCHECKED_CAST")
        model.subList.forEach { findLeaf(it as M, list) }
      } else {
        list += model
      }
    }
  }
}

@BindingAdapter("binding_rv_dataList")
internal fun <M : IVhModelType> RecyclerView.bindingSetList(list: List<M>?) {
  @Suppress("UNCHECKED_CAST")
  (adapter as? IMutableRvAdapter<M>)?.list = list.orEmpty()
}

@BindingAdapter("binding_rv_refreshItems")
internal fun <M : IVhModelType> RecyclerView.bindingRefreshItems(vararg items: Triple<Int, M, Any?>) {
  @Suppress("UNCHECKED_CAST")
  (adapter as? IMutableRvAdapter<M>)?.refreshItems(*items)
}
