package io.goooler.demoapp.adapter.rv.core

import androidx.annotation.IntRange
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
  private val listener: ItemChangeListener,
  private val longPressDragEnabled: Boolean = true,
  private val itemViewSwipeEnabled: Boolean = false
) : ItemTouchHelper.Callback() {

  override fun isLongPressDragEnabled(): Boolean = longPressDragEnabled

  override fun isItemViewSwipeEnabled(): Boolean = itemViewSwipeEnabled

  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder
  ): Int {
    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
      ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    return makeMovementFlags(dragFlags, swipeFlags)
  }

  override fun onMove(
    recyclerView: RecyclerView,
    from: RecyclerView.ViewHolder,
    to: RecyclerView.ViewHolder
  ): Boolean {
    listener.onItemMove(from.bindingAdapterPosition, to.bindingAdapterPosition)
    return true
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    listener.onItemDismiss(viewHolder.bindingAdapterPosition)
  }

  interface ItemChangeListener {

    fun onItemMove(@IntRange(from = 0) fromPosition: Int, @IntRange(from = 0) toPosition: Int)

    fun onItemDismiss(@IntRange(from = 0) position: Int)
  }
}
