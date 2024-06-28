package io.github.goooler.adapter.rv.diff

import androidx.recyclerview.widget.DiffUtil

public open class DiffCallback<M : IDiffVhModelType> : DiffUtil.ItemCallback<M>() {

  /**
   * Call this first.
   */
  public override fun areItemsTheSame(oldItem: M, newItem: M): Boolean = oldItem.isItemTheSame(newItem)

  /**
   * Call this second.
   */
  public override fun areContentsTheSame(oldItem: M, newItem: M): Boolean =
    oldItem.isContentTheSame(newItem)

  public override fun getChangePayload(oldItem: M, newItem: M): Any? = oldItem.getPayloads(newItem)
}
