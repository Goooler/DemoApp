@file:Suppress("unused")

package io.github.goooler.adapter.rv.core

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * When recyclerView's layoutManager is [GridLayoutManager], item can fix span size.
 * When recyclerView's layoutManager is [StaggeredGridLayoutManager], just can set SPAN_SIZE_FULL!
 */
public interface ISpanSize {
  /**
   * Get span size. How many lattices does a model occupy.
   */
  public val spanSize: Int get() = SPAN_SIZE_SINGLE

  public companion object {
    /**
     * fill one line.
     */
    public const val SPAN_SIZE_FULL: Int = -1
    public const val SPAN_SIZE_SINGLE: Int = 1
    public const val SPAN_SIZE_DOUBLE: Int = 2
    public const val SPAN_SIZE_TRIPLE: Int = 3
    public const val SPAN_SIZE_QUADRUPLE: Int = 4
    public const val SPAN_SIZE_QUINTUPLE: Int = 5
  }
}
