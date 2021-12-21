package io.goooler.demoapp.common.base.binding

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.common.base.theme.BaseThemeActivity
import io.goooler.demoapp.common.util.inflateBinding

abstract class BaseBindingActivity<VB : ViewDataBinding> : BaseThemeActivity(), IBinding<VB> {

  override lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = (inflateBinding(layoutInflater) as VB).also {
      it.lifecycleOwner = this
      setContentView(it.root)
    }
  }
}
