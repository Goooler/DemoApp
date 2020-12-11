package io.goooler.demoapp.adapter.rv.core

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * Created on 2020/10/21.
 *
 * The unified management class of ViewTypeDelegate.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 * @see ViewTypeDelegate
 */
class ViewTypeDelegateManager<M : IVhModelType> {

  private val mIVDs = SparseArray<ViewTypeDelegate<ViewDataBinding, M>>()

  /**
   * When creating viewHolder. if VTD.getViewType() == viewType executes VTD.onCreateVH().
   *
   * @param binding ViewDataBinding
   * @param viewType viewType
   */
  internal fun onCreateVH(binding: ViewDataBinding, @LayoutRes viewType: Int) {
    if (mIVDs.size() == 0) return
    mIVDs.get(viewType)?.onCreateVH(binding)
  }

  /**
   * When bind viewHolder. if VTD.getViewType() == model.viewType executes VTD.onBindVh().
   *
   * @param binding ViewDataBinding
   * @param model model
   */
  internal fun onBindVH(binding: ViewDataBinding, model: M) {
    if (mIVDs.size() == 0) return
    mIVDs.get(model.viewType)?.onBindVH(binding, model)
  }

  /**
   * Add VTD into manager.
   *
   * @param ivd VTD
   */
  @Suppress("UNCHECKED_CAST")
  fun <X : ViewDataBinding, Y : M> add(ivd: ViewTypeDelegate<X, Y>) {
    mIVDs.put(ivd.viewType, ivd as ViewTypeDelegate<ViewDataBinding, M>)
  }

  /**
   * Eliminate all item's VTD.
   */
  fun clear() {
    mIVDs.clear()
  }
}
