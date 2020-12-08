@file:Suppress("unused")

package io.goooler.demoapp.adapter.rv.core

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created on 2020/11/10.
 *
 * When recyclerView's layoutManager is [GridLayoutManager], item can fix span size.
 * When recyclerView's layoutManager is [StaggeredGridLayoutManager], just can set SPAN_SIZE_FULL!
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface ISpanSize {
    /**
     * Get span size. How many lattices does a model occupy.
     */
    val spanSize: Int get() = SPAN_SIZE_SINGLE

    companion object {
        /**
         * fill one line.
         */
        const val SPAN_SIZE_FULL = -1
        const val SPAN_SIZE_SINGLE = 1
        const val SPAN_SIZE_DOUBLE = 2
        const val SPAN_SIZE_TRIPLE = 3
        const val SPAN_SIZE_QUADRUPLE = 4
        const val SPAN_SIZE_QUINTUPLE = 5
    }
}
