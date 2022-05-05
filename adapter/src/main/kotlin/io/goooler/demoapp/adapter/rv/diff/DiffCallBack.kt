package io.goooler.demoapp.adapter.rv.diff

import androidx.recyclerview.widget.DiffUtil

open class DiffCallBack<M : IDiffVhModelType> : DiffUtil.ItemCallback<M>() {

  /**
   * Call this first.
   */
  override fun areItemsTheSame(oldItem: M, newItem: M): Boolean = oldItem.isItemTheSame(newItem)

  /**
   * Call this second.
   */
  override fun areContentsTheSame(oldItem: M, newItem: M): Boolean =
    oldItem.isContentTheSame(newItem)

  override fun getChangePayload(oldItem: M, newItem: M): Any? = null
}
