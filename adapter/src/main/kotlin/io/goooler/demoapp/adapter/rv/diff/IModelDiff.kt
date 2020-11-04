package io.goooler.demoapp.adapter.rv.diff

import io.goooler.demoapp.adapter.rv.base.IModelType

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IModelDiff<D : IModelDiff<D>> : IModelType {

    /**
     * 默认同一个对象就是同一个 item
     */
    fun isItemTheSame(that: D): Boolean = this == that

    /**
     * 默认同一个对象内容一样
     */
    fun isContentTheSame(that: D): Boolean = this == that
}
