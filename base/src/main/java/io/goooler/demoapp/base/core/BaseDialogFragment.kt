package io.goooler.demoapp.base.core

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import io.goooler.demoapp.base.util.showToastInMainThread

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseDialogFragment : DialogFragment() {

    var dismissListener: OnDismissListener? = null

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass).apply {
            lifecycle.addObserver(this)
        }
    }

    @MainThread
    protected fun showToast(@StringRes strResId: Int) {
        showToast(getString(strResId))
    }

    @MainThread
    protected fun showToast(text: String) {
        text.showToastInMainThread(requireContext())
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}