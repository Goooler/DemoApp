package io.goooler.demoapp.base.core

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.goooler.demoapp.base.util.ToastUtil

/**
 * Fragment 基类，封装通用方法
 */
abstract class BaseFragment : Fragment() {

    /**
     * 调用 activity 的返回
     */
    protected fun onBackPressed() {
        activity?.onBackPressed()
    }

    /**
     * 调用 activity 的结束
     */
    protected fun finish() {
        activity?.finish()
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要添加的 fragment
     * @param isAddToBackStack  将要添加的 fragment 是否要添加到返回栈
     * @param tag               fragment 的 tag
     */
    protected fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = false,
        tag: String? = null
    ) {
        childFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            add(containerViewId, fragment, tag)
        }
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要替换的 fragment
     * @param isAddToBackStack  将要替换的 fragment 是否要添加到返回栈
     * @param tag               fragment 的 tag
     */
    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = true,
        tag: String? = null
    ) {
        childFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            replace(containerViewId, fragment, tag)
        }
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass).apply {
            lifecycle.addObserver(this)
            observeVm(this)
        }
    }

    protected fun <T : BaseViewModel> getViewModelOfActivity(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass).apply {
            lifecycle.addObserver(this)
            observeVm(this)
        }
    }

    protected fun showToast(@StringRes textId: Int) {
        ToastUtil.showToastInMainThread(requireContext(), getString(textId))
    }

    protected fun showToast(text: String) {
        ToastUtil.showToastInMainThread(requireContext(), text)
    }

    private fun observeVm(vm: BaseViewModel) {
        vm.toast.observe(this, Observer {
            showToast(it)
        })
    }
}
