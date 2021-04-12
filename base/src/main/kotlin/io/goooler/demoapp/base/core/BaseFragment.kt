package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import io.goooler.demoapp.base.core.IFragment.Companion.dispatchBackPress

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
    view?.dispatchBackPress(::onBackPressed)
  }

  protected fun startService(service: Intent): ComponentName? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
      activity?.startForegroundService(service)
    else
      activity?.startService(service)
  }
}

interface IFragment {

  fun onBackPressed(): Boolean

  fun finish()

  companion object {

    fun View.dispatchBackPress(block: () -> Boolean) {
      isFocusableInTouchMode = true
      requestFocus()
      setOnKeyListener { _, keyCode, event ->
        val flag = keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
        if (flag) block() else false
      }
    }
  }
}
