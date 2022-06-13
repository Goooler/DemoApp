package io.goooler.demoapp.common.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.common.base.theme.BaseThemeFragment
import io.goooler.demoapp.common.util.inflateBinding

abstract class BaseBindingFragment<VB : ViewDataBinding> :
  BaseFragment(),
  IBindingFragment<VB> {
  private var _binding: VB? = null

  override lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = inflateBinding(layoutInflater)
    binding = _binding ?: throw IllegalArgumentException("Binding is null in $this")
    initOnce()
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

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
