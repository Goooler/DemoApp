package io.github.goooler.adapter.rv.internal

import androidx.annotation.LayoutRes
import androidx.collection.SparseArrayCompat
import androidx.collection.set
import androidx.databinding.ViewDataBinding
import io.github.goooler.adapter.rv.core.IVhModelType

/**
 * The unified management class of ViewTypeDelegate.
 */
internal class ViewTypeDelegateManager<M : IVhModelType> {

  private val ivDs = SparseArrayCompat<ViewTypeDelegate<M>>()

  /**
   * When creating viewHolder. if VTD.getViewType() == viewType executes VTD.onCreateVH().
   *
   * @param binding ViewDataBinding
   * @param viewType viewType
   */
  fun onCreateVH(binding: ViewDataBinding, @LayoutRes viewType: Int) {
    if (ivDs.isEmpty) return
    ivDs[viewType]?.onCreateVH(binding)
  }

  /**
   * When bind viewHolder. if VTD.getViewType() == model.viewType executes VTD.onBindVh().
   *
   * @param binding ViewDataBinding
   * @param model model
   */
  fun onBindVH(binding: ViewDataBinding, model: M, payloads: List<Any>) {
    if (ivDs.isEmpty) return
    ivDs[model.viewType]?.onBindVH(binding, model, payloads)
  }

  /**
   * Add VTD into manager.
   *
   * @param ivd VTD
   */
  @Suppress("UNCHECKED_CAST")
  fun <T : M> add(ivd: ViewTypeDelegate<T>) {
    ivDs[ivd.viewType] = ivd as ViewTypeDelegate<M>
  }

  /**
   * Eliminate all item's VTD.
   */
  fun clear() {
    ivDs.clear()
  }
}
