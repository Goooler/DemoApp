package io.github.goooler.adapter.rv.core

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

public class ItemTouchHelperCallback(
  private val listener: ItemChangeListener,
  private val longPressDragEnabled: Boolean = true,
  private val itemViewSwipeEnabled: Boolean = false,
) : ItemTouchHelper.Callback() {

  public override fun isLongPressDragEnabled(): Boolean = longPressDragEnabled

  public override fun isItemViewSwipeEnabled(): Boolean = itemViewSwipeEnabled

  public override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
  ): Int {
    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
      ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    return makeMovementFlags(dragFlags, swipeFlags)
  }

  public override fun onMove(
    recyclerView: RecyclerView,
    from: RecyclerView.ViewHolder,
    to: RecyclerView.ViewHolder,
  ): Boolean {
    listener.onItemMove(from.bindingAdapterPosition, to.bindingAdapterPosition)
    return true
  }

  public override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    listener.onItemDismiss(viewHolder.bindingAdapterPosition)
  }

  public interface ItemChangeListener {

    public fun onItemMove(fromPosition: Int, toPosition: Int)

    public fun onItemDismiss(position: Int)
  }
}
