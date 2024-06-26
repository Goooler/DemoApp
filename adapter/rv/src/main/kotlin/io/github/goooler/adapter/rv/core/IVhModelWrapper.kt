package io.github.goooler.adapter.rv.core

/**
 * Model wrapper. M type need same as constrained [IVhModelType].
 */
public interface IVhModelWrapper<M : IVhModelType> : IVhModelType {

  /**
   * If [viewType] return value is not -1, this [IVhModelWrapper] self will be as a node.
   */
  override val viewType: Int get() = -1

  /**
   * As sub model list.
   */
  public val subList: Iterable<M>
}
