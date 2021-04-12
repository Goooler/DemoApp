package io.goooler.demoapp.common.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.common.R
import io.goooler.demoapp.common.base.BaseThemeDialogFragment
import io.goooler.demoapp.common.databinding.CommonFullScreenDialogFragmentBinding

class FullScreenDialogFragment :
  BaseThemeDialogFragment<CommonFullScreenDialogFragmentBinding>(R.layout.common_full_screen_dialog_fragment) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE, R.style.DialogFullScreenTheme)
  }

  override fun initOnce() {
    binding.ivCenter.setOnClickListener {
      dismiss()
    }
  }

  override fun onBackPressed(): Boolean {
    return true
  }

  companion object {
    fun show(manager: FragmentManager) = FullScreenDialogFragment().show(manager, null)
  }
}
