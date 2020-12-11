package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.DiffUtil

open class DiffCallBack<M : IDiffVhModelType> : DiffUtil.ItemCallback<M>() {

  /**
   * Judge whether the contents of two items are the same.
   */
  override fun areContentsTheSame(oldItem: M, newItem: M): Boolean =
    oldItem.isContentTheSame(newItem)

  /**
   * Judge whether two items use the same item.
   */
  override fun areItemsTheSame(oldItem: M, newItem: M): Boolean = oldItem.isItemTheSame(newItem)

  /**
   * Advanced usage, used to extract changes and refresh local variables accurately.
   * Not for the time being.
   */
  override fun getChangePayload(oldItem: M, newItem: M): Any? = null
}
