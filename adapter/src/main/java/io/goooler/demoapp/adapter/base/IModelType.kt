package io.goooler.demoapp.adapter.base

/**
 * Created on 2019/08/22.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IModelType {
    /**
     * 获取 viewType，把layout id 当作 viewType。
     */
    fun getViewType(): Int
}