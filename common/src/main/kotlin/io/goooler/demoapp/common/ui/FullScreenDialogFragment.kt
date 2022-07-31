package io.goooler.demoapp.common.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.common.R
import io.goooler.demoapp.common.base.binding.BaseBindingDialogFragment
import io.goooler.demoapp.common.databinding.CommonFullScreenDialogFragmentBinding

class FullScreenDialogFragment :
  BaseBindingDialogFragment<CommonFullScreenDialogFragmentBinding>() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.let {
      it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    dialog?.window?.run {
      setWindowAnimations(R.style.CommonDialogBottomAnim)
      attributes = attributes?.apply {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        gravity = Gravity.BOTTOM
      }
    }
  }

  override fun initOnce() {
    binding.ivCenter.setOnClickListener {
      dismiss()
    }
  }

  override fun isCancelable(): Boolean = false

  override fun onBackPressed(): Boolean = true

  companion object {
    fun show(manager: FragmentManager) = FullScreenDialogFragment().show(manager, null)
  }
}
