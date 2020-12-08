package io.goooler.demoapp.adapter.rv.diff

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
     * Whether the content is the same. By default, the content always different.
     *
     * @param that other model
     */
    fun isContentTheSame(that: IDiffVhModelType): Boolean = false

    /**
     * Whether the item is the same. By default, the same object is the same item.
     *
     * @param that other model
     */
    fun isItemTheSame(that: IDiffVhModelType): Boolean = this == that
}
