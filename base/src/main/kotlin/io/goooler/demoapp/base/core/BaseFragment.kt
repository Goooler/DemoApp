package io.goooler.demoapp.base.core

import android.view.KeyEvent
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), IFragment {

  override fun onBackPressed(): Boolean {
    activity?.onBackPressed()
    return true
  }

  override fun finish() {
    activity?.finish()
  }

  @CallSuper
  override fun onResume() {
    super.onResume()
    view?.dispatchBackPress()
  }
}

sealed interface IFragment {

  fun onBackPressed(): Boolean

  fun finish()

  fun View.dispatchBackPress() {
    isFocusableInTouchMode = true
    requestFocus()
    setOnKeyListener { _, keyCode, event ->
      val flag = keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
      if (flag) onBackPressed() else false
    }
  }
}
