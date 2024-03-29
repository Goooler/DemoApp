package io.goooler.demoapp.common.base.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import io.goooler.demoapp.base.core.BaseDialogFragment

abstract class BaseBindingDialogFragment<VB : ViewBinding> :
  BaseDialogFragment(),
  IBindingFragment<VB> {

  override lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = inflateBinding(layoutInflater)
    initOnce()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (binding as? androidx.databinding.ViewDataBinding)?.lifecycleOwner = viewLifecycleOwner
  }
}
