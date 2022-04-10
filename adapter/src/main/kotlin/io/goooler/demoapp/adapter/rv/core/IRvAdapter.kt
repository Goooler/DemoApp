package io.goooler.demoapp.adapter.rv.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2020/10/21.
 *
 * Please let your [RecyclerView.Adapter] implements IExtAdapter.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
sealed interface IRvAdapter<M : IVhModelType> {

  /**
   * What to do when creating the viewHolder for all.
   */
  fun onCreateVHForAll(binding: ViewDataBinding)

  /**
   * What to do when binding the viewHolder for all.
   */
  fun onBindVHForAll(binding: ViewDataBinding, model: M)

  /**
   * Create BaseViewHolder.
   */
  fun createVH(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(parent.context),
      viewType,
      parent,
      false
    )
    return BindingViewHolder(binding)
  }

  /**
   * Init ViewTypeDelegateManager. You can add VTDs.
   */
  fun initManager(manager: ViewTypeDelegateManager<M>) {}

  /**
   * Get item by position.
   */
  fun getModel(@IntRange(from = 0) position: Int): M?
}

interface IMutableRvAdapter<M : IVhModelType> : IRvAdapter<M> {

  /**
   * Set or Get data list.
   */
  var list: List<M>

  /**
   * Refresh some items.
   */
  fun refreshItems(items: List<M>)

  fun removeItem(@IntRange(from = 0) index: Int)

  fun removeItem(item: M)
}

@BindingAdapter(value = ["binding_rv_dataList"], requireAll = true)
fun <M : IVhModelType> RecyclerView.setList(list: List<M>?) {
  @Suppress("UNCHECKED_CAST")
  (adapter as? IMutableRvAdapter<M>)?.list = list.orEmpty()
}

@BindingAdapter(value = ["binding_rv_refreshItems"], requireAll = true)
fun <M : IVhModelType> RecyclerView.refreshItems(items: List<M>?) {
  @Suppress("UNCHECKED_CAST")
  (adapter as? IMutableRvAdapter<M>)?.refreshItems(items.orEmpty())
}
