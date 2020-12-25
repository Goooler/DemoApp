package io.goooler.demoapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseDialogFragment

abstract class BaseThemeDialogFragment<VB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
  BaseDialogFragment(),
  ITheme {

  protected lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
    initView()
  }

  protected open fun initView() {}

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lifecycleOwner = viewLifecycleOwner
  }
}
