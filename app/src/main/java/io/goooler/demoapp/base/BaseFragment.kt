package io.goooler.demoapp.base

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.goooler.demoapp.util.ToastUtil

/**
 * Fragment 基类，封装通用方法
 */
abstract class BaseFragment : Fragment() {

    /**
     * @param containerId 容器id
     * @param fragment
     * @param isAddToBackStack 将要替换的fragment是否要添加到返回栈
     */
    protected fun addFragment(@IdRes containerId: Int, fragment: Fragment, isAddToBackStack: Boolean = false, tag: String? = null) {
        getFragmentTransaction(isAddToBackStack, tag).add(containerId, fragment, tag).commit()
    }

    /**
     * @param containerId
     * @param fragment
     * @param isAddToBackStack 将要替换的fragment是否要添加到返回栈
     * @param tag              fragment的tag
     */
    protected fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, isAddToBackStack: Boolean = true, tag: String? = null) {
        getFragmentTransaction(isAddToBackStack, tag).replace(containerId, fragment, tag).commit()
    }

    private fun getFragmentTransaction(isAddToBackStack: Boolean, tag: String?): FragmentTransaction {
        return childFragmentManager.beginTransaction().apply {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
        }
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProviders.of(this@BaseFragment).get(modelClass).apply {
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
        vm.toast.observe(this@BaseFragment, Observer {
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
