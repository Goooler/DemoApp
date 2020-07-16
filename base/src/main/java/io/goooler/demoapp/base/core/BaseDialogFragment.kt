package io.goooler.demoapp.base.core

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.goooler.demoapp.base.util.ToastUtil

abstract class BaseDialogFragment : DialogFragment() {

    var dismissListener: OnDismissListener? = null

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass).apply {
            lifecycle.addObserver(this)
            observeVm(this)
        }
    }

    private fun observeVm(vm: BaseViewModel) {
        vm.toast.observe(this, Observer<String> {
            showToast(it)
        })
    }

    protected fun showToast(@StringRes textId: Int) {
        ToastUtil.showToastInMainThread(requireContext(), getString(textId))
    }

    protected fun showToast(text: String) {
        ToastUtil.showToastInMainThread(requireContext(), text)
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}