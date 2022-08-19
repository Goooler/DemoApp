package io.goooler.demoapp.common.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.common.base.binding.BaseBindingActivity.Companion.inflateBinding

abstract class BaseBindingFragment<VB : ViewDataBinding> :
  BaseFragment(),
  IBindingFragment<VB> {
  private var _binding: VB? = null

  /**
   * You can't call [binding] after [onDestroyView]
   */
  override val binding: VB get() = _binding ?: throw IllegalArgumentException("Binding has been destroyed")

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = inflateBinding(layoutInflater)
    initOnce()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.lifecycleOwner = viewLifecycleOwner
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
