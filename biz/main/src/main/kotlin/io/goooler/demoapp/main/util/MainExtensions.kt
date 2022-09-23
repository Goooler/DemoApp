package io.goooler.demoapp.main.util

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.main.BRBridge as BR

internal fun ViewDataBinding.bindListener(listener: Any) {
  setVariable(BR.listener, listener)
}

internal fun ViewDataBinding.bindModel(model: Any) {
  setVariable(BR.model, model)
}
