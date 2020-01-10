package io.goooler.demoapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.ToastUtil

open class BaseDialogFragment : DialogFragment() {

    private var fManager: FragmentManager? = null
    private var fragmentTag: String? = null

    override fun show(manager: FragmentManager, tag: String?) {
        fManager = manager
        fragmentTag = tag
        if (DialogCollector.isShowing().not()) {
            super.show(manager, tag)
        }
        DialogCollector.addDialog(this@BaseDialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.d("")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun dismiss() {
        super.dismiss()
        DialogCollector.removeDialog()
        fManager?.let {
            DialogCollector.getDialog()?.justShow(it, fragmentTag)
        }
    }

    private fun justShow(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProviders.of(this).get(modelClass).apply {
            lifecycle.addObserver(this)
            observeVm(this)
        }
    }

    protected fun <T : BaseViewModel> getViewModelOfActivity(modelClass: Class<T>): T {
        return ViewModelProviders.of(requireActivity()).get(modelClass).apply {
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
        ToastUtil.showToast(textId)
    }

    protected fun showToast(text: String) {
        ToastUtil.showToast(text)
    }
}