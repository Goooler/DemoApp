package io.goooler.demoapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseDialogFragment
import io.goooler.demoapp.common.util.inflateBinding

abstract class BaseThemeDialogFragment<VB : ViewDataBinding> :
  BaseDialogFragment(),
  ITheme,
  IFragmentCommon {

  protected lateinit var binding: VB

  override fun showLoading() {}

  override fun hideLoading() {}

  override fun initOnce() {}

  @Suppress("UNCHECKED_CAST")
  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = inflateBinding(layoutInflater)
    initOnce()
  }

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lifecycleOwner = viewLifecycleOwner
  }
}
