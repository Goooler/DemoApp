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

  /**
   * If [viewType] return value is not -1, this [IVhModelWrapper] self will be as a node.
   */
  override val viewType: Int get() = -1

  /**
   * As sub model list.
   */
  val subList: Iterable<M>
}
