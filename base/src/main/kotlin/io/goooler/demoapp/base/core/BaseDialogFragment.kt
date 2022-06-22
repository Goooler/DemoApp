@file:Suppress("MemberVisibilityCanBePrivate")

package io.goooler.demoapp.base.core

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment(), IFragment {

  var onDismissListener: DialogInterface.OnDismissListener? = null
  var onCancelListener: DialogInterface.OnCancelListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dispatchBackPress()
  }

  override fun onResume() {
    super.onResume()
    dialog?.run {
      setCanceledOnTouchOutside(isCancelable)
      setCancelable(isCancelable)
    }
  }

  override fun onDismiss(dialog: DialogInterface) {
    onDismissListener?.onDismiss(dialog)
    super.onDismiss(dialog)
  }

  override fun onCancel(dialog: DialogInterface) {
    onCancelListener?.onCancel(dialog)
    super.onCancel(dialog)
  }

  override fun finish() {
    activity?.finish()
  }
}
