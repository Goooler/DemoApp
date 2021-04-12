package io.goooler.demoapp.base.core

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
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
    view?.run {
      isFocusableInTouchMode = true
      requestFocus()
      setOnKeyListener { _, keyCode, event ->
        val flag = keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
        if (flag) onBackPressed() else false
      }
    }
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
}
