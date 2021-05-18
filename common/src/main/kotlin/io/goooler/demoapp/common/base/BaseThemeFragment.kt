package io.goooler.demoapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.common.util.inflateBinding

abstract class BaseThemeFragment<VB : ViewDataBinding> :
  BaseFragment(),
  ITheme,
  IFragmentCommon {

  protected lateinit var binding: VB

  @MainThread
  override fun showLoading() {}

  @MainThread
  override fun hideLoading() {}

  @MainThread
  override fun initOnce() {}

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

sealed interface IFragmentCommon {
  fun initOnce()
}
