package io.goooler.demoapp.base.core

import androidx.fragment.app.DialogFragment

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseDialogFragment : DialogFragment() {

    var dismissListener: OnDismissListener? = null

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    fun interface OnDismissListener {
        fun onDismiss()
    }
}