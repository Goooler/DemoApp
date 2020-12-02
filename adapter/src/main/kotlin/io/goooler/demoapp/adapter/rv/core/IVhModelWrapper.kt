package io.goooler.demoapp.adapter.rv.core

/**
 * Created on 2020/10/23.
 *
 * Model wrapper. M type need same as constrained [IVhModelType].
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IVhModelWrapper<M : IVhModelType> : IVhModelType {

    override val viewType: Int get() = throw RuntimeException("Wrapper can not getViewType()!")

    /**
     * As sub model list.
     */
    fun asList(): List<M>
}