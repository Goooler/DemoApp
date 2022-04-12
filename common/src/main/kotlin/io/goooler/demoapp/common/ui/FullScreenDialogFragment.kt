package io.goooler.demoapp.common.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.common.R
import io.goooler.demoapp.common.base.binding.BaseBindingDialogFragment
import io.goooler.demoapp.common.databinding.CommonFullScreenDialogFragmentBinding

class FullScreenDialogFragment :
  BaseBindingDialogFragment<CommonFullScreenDialogFragmentBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE, R.style.CommonDialogFullScreenTheme)
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
