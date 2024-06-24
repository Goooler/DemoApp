package io.goooler.demoapp.adapter.rv.core

import androidx.annotation.LayoutRes

/**
 * Created on 2020/10/21.
 *
 * A class model(same viewType) corresponds to a delegate object.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface ViewTypeDelegate<M : IVhModelType> : IRvBinding<M> {

  /**
   * The delegate takes effect when this.getViewType() == model.getViewType().
   */
  @get:LayoutRes
  val viewType: Int
}
