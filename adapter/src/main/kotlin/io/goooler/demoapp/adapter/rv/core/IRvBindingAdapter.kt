package io.goooler.demoapp.adapter.rv.core

import androidx.databinding.ViewDataBinding

interface IRvBindingAdapter<M : IVhModelType> {

  /**
   * What to do when creating the viewHolder.
   *
   * @param binding ViewDataBinding
   */
  fun onCreateVH(binding: ViewDataBinding)

  /**
   * What to do when binding the viewHolder.
   *
   * @param binding ViewDataBinding
   * @param model model
   */
  fun onBindVH(binding: ViewDataBinding, model: M, payloads: List<Any>)
}
