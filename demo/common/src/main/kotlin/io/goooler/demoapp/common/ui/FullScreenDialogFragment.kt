package io.goooler.demoapp.common.ui

import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.common.R
import io.goooler.demoapp.common.base.binding.BaseBindingDialogFragment
import io.goooler.demoapp.common.databinding.CommonFullScreenDialogFragmentBinding

class FullScreenDialogFragment :
  BaseBindingDialogFragment<CommonFullScreenDialogFragmentBinding>() {

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
