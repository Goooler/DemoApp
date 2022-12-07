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
   * Whether the item ([ViewHolder]) is the same.
   * If your items have unique ids, this method should check their id equality.
   *
   * @param that other model
   * @return by default, the same object is the same item.
   */
  fun isItemTheSame(that: IDiffVhModelType): Boolean = this == that

  /**
   * Whether the content is the same.
   * You should return whether the items' visual representations are the same.
   *
   * @param that other model
   * @return by default, checking if they are equals.
   */
  fun isContentTheSame(that: IDiffVhModelType): Boolean = this == that
}
