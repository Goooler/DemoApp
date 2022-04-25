package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.goooler.demoapp.adapter.rv.core.IVhModelType

/**
 * Created on 2020/10/22.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IDiffVhModelType : IVhModelType {

  /**
   * Whether the item ([ViewHolder]) is the same. By default, the same [viewType] is the same item.
   *
   * @param that other model
   */
  fun isItemTheSame(that: IDiffVhModelType): Boolean = this.viewType == that.viewType

  /**
   * Whether the content is the same. By default, checking if they are equals.
   *
   * @param that other model
   */
  fun isContentTheSame(that: IDiffVhModelType): Boolean = this == that
}
