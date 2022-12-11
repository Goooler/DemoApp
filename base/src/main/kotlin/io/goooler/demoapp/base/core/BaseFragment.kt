package io.goooler.demoapp.base.core

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), IFragment {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activity?.onBackPressedDispatcher?.addCallback(this) {
      isEnabled = onBackPressed()
      @Suppress("DEPRECATION")
      if (!isEnabled) activity?.onBackPressed()
      isEnabled = true
    }
  }

  override fun finish() {
    activity?.finish()
  }
}

sealed interface IFragment {

  fun onBackPressed(): Boolean = false

  fun finish()
}
