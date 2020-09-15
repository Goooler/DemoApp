package io.goooler.demoapp.base.core

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import io.goooler.demoapp.base.util.showToastInMainThread

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseDialogFragment : DialogFragment() {

    var dismissListener: OnDismissListener? = null

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    @MainThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getString(strResId))
    }

    @MainThread
    protected fun showToast(text: String) {
        text.showToastInMainThread(requireContext())
    }

    fun interface OnDismissListener {
        fun onDismiss()
    }
}