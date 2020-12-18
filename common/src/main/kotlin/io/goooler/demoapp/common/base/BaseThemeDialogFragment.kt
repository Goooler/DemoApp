package io.goooler.demoapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseDialogFragment
import io.goooler.demoapp.base.util.unsafeLazy

abstract class BaseThemeDialogFragment<VB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
  BaseDialogFragment(),
  ITheme {

  protected val binding: VB by unsafeLazy {
    DataBindingUtil.inflate<VB>(layoutInflater, layoutId, null, false).also {
      it.lifecycleOwner = viewLifecycleOwner
    }
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root
}
