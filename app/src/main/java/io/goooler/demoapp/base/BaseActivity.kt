package io.goooler.demoapp.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import io.goooler.demoapp.model.Constants
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.ToastUtil

/**
 * Activity 基类，封装通用方法
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity 入栈日志记录
        LogUtil.d(Constants.BASE_ACTIVITY, javaClass.simpleName)
        // activity 入栈 List<Activity> 记录
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // activity 出栈 List<Activity> 移除
        ActivityCollector.removeActivity(this)
    }

    protected fun showToast(@StringRes textId: Int) {
        ToastUtil.showToast(textId)
    }

    protected fun showToast(text: String) {
        ToastUtil.showToast(text)
    }

    protected fun <T : ViewDataBinding> inflate(@LayoutRes layoutId: Int): T {
        return DataBindingUtil.setContentView(this, layoutId)
    }

    /**
     * @param containerId 容器id
     * @param fragment
     * @param isAddToBackStack 将要替换的fragment是否要添加到返回栈
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected fun addFragment(@IdRes containerId: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().run {
            add(containerId, fragment)
            if (isAddToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }

    /**
     * @param containerId
     * @param fragment
     * @param isAddToBackStack 将要替换的fragment是否要添加到返回栈
     * @param tag              fragment的tag
     */
    protected fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, isAddToBackStack: Boolean, tag: String? = null) {
        supportFragmentManager.beginTransaction().run {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            replace(containerId, fragment, tag).commit()
        }
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProviders.of(this).get(modelClass).apply {
            lifecycle.addObserver(this)
            toast.observe(this@BaseActivity, Observer<String> { msg ->
                showToast(msg)
            })
        }
    }
}
