package io.github.goooler.adapter.rv.core

import androidx.databinding.ViewDataBinding

public interface IRvBinding<M : IVhModelType> {

  /**
   * What to do when creating the viewHolder.
   *
   * @param binding ViewDataBinding
   */
  public fun onCreateVH(binding: ViewDataBinding)

  /**
   * What to do when binding the viewHolder.
   *
   * @param binding ViewDataBinding
   * @param model model
   */
  public fun onBindVH(binding: ViewDataBinding, model: M, payloads: List<Any>)
}
