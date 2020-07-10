package io.goooler.demoapp.adapter.rv.base

/**
 * Created on 2019/08/22.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IModelType {
    /**
     * 获取 viewType，把 layout id 当作 viewType。
     * @LayoutRes
     */
    val viewType: Int

    /**
     * 默认的 span 大小
     */
    fun getSpanSize(): Int = 1
}