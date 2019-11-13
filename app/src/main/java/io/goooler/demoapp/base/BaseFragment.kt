package io.goooler.demoapp.base

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.goooler.demoapp.util.ToastUtil

/**
 * Fragment 基类，封装通用方法
 */
abstract class BaseFragment : Fragment() {

    protected fun <T : ViewDataBinding> inflate(@LayoutRes layoutId: Int): T {
        return DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
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
