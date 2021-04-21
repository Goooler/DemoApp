package io.goooler.demoapp.base.core

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), IFragment {

  override fun onBackPressed(): Boolean {
    activity?.onBackPressed()
    return true
  }

  override fun finish() {
    activity?.finish()
  }

  override fun onResume() {
    super.onResume()
    view?.dispatchBackPress()
  }
}

interface IFragment {

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
