package io.goooler.demoapp.base.core

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), IFragment {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dispatchBackPress()
  }

  override fun finish() {
    activity?.finish()
  }
}

sealed interface IFragment {

  fun onBackPressed(): Boolean = false

  fun finish()

  fun Fragment.dispatchBackPress() {
    activity?.onBackPressedDispatcher?.addCallback(this) {
      isEnabled = onBackPressed()
      if (!isEnabled) activity?.onBackPressed()
      isEnabled = true
    }
  }
}
