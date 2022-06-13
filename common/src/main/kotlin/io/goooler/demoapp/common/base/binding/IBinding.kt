package io.goooler.demoapp.common.base.binding

import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding

internal sealed interface IBinding<VB : ViewDataBinding> {
  val binding: VB
}

internal sealed interface IBindingFragment<VB : ViewDataBinding> : IBinding<VB> {

  @MainThread
  fun initOnce() {
  }
}
