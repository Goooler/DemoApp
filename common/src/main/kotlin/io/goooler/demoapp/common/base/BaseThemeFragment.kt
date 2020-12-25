package io.goooler.demoapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseFragment

abstract class BaseThemeFragment<VB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
  BaseFragment(),
  ITheme {

  protected lateinit var binding: VB

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
    initOnce()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root

  protected open fun initOnce() {}

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lifecycleOwner = viewLifecycleOwner
  }
}
