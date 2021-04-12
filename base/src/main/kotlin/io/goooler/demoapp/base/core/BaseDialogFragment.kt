package io.goooler.demoapp.base.core

import android.view.KeyEvent
import androidx.fragment.app.DialogFragment

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseDialogFragment : DialogFragment(), IFragment {

  var dismissListener: OnDismissListener? = null

  override fun onBackPressed(): Boolean {
    dismiss()
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

  override fun dismiss() {
    dismissListener?.onDismiss()
    super.dismiss()
  }

  fun interface OnDismissListener {
    fun onDismiss()
  }
}
