package io.goooler.demoapp.adapter.rv.core

import androidx.annotation.LayoutRes
import androidx.collection.SparseArrayCompat
import androidx.collection.set
import androidx.databinding.ViewDataBinding

/**
 * Created on 2020/10/21.
 *
 * The unified management class of ViewTypeDelegate.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
class ViewTypeDelegateManager<M : IVhModelType> {

  private val mIVDs = SparseArrayCompat<ViewTypeDelegate<ViewDataBinding, M>>()

  /**
   * When creating viewHolder. if VTD.getViewType() == viewType executes VTD.onCreateVH().
   *
   * @param binding ViewDataBinding
   * @param viewType viewType
   */
  internal fun onCreateVH(binding: ViewDataBinding, @LayoutRes viewType: Int) {
    if (mIVDs.isEmpty) return
    mIVDs[viewType]?.onCreateVH(binding)
  }

  /**
   * When bind viewHolder. if VTD.getViewType() == model.viewType executes VTD.onBindVh().
   *
   * @param binding ViewDataBinding
   * @param model model
   */
  internal fun onBindVH(binding: ViewDataBinding, model: M) {
    if (mIVDs.isEmpty) return
    mIVDs[model.viewType]?.onBindVH(binding, model)
  }

  /**
   * Add VTD into manager.
   *
   * @param ivd VTD
   */
  @Suppress("UNCHECKED_CAST")
  fun <X : ViewDataBinding, Y : M> add(ivd: ViewTypeDelegate<X, Y>) {
    mIVDs[ivd.viewType] = ivd as ViewTypeDelegate<ViewDataBinding, M>
  }

  /**
   * Eliminate all item's VTD.
   */
  fun clear() {
    mIVDs.clear()
  }
}
