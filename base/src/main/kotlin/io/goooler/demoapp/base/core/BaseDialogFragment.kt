@file:Suppress("MemberVisibilityCanBePrivate")

package io.goooler.demoapp.base.core

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment(), IFragment {

  var onDismissListener: DialogInterface.OnDismissListener? = null
  var onCancelListener: DialogInterface.OnCancelListener? = null

  override fun onBackPressed(): Boolean {
    dismiss()
    return true
  }

  override fun finish() {
    activity?.finish()
  }

  override fun onResume() {
    super.onResume()
    dialog?.run {
      setCanceledOnTouchOutside(isCancelable)
      setCancelable(isCancelable)
    }
    view?.dispatchBackPress()
  }

  override fun onDismiss(dialog: DialogInterface) {
    onDismissListener?.onDismiss(dialog)
    super.onDismiss(dialog)
  }

  override fun onCancel(dialog: DialogInterface) {
    onCancelListener?.onCancel(dialog)
    super.onCancel(dialog)
  }
}
