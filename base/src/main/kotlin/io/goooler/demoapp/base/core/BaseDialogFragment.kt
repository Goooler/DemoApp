package io.goooler.demoapp.base.core

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
    dialog?.run {
      setCanceledOnTouchOutside(isCancelable)
      setCancelable(isCancelable)
    }
    view?.dispatchBackPress()
  }

  override fun dismiss() {
    dismissListener?.onDismiss()
    super.dismiss()
  }

  fun interface OnDismissListener {
    fun onDismiss()
  }
}
