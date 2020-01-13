package io.goooler.demoapp.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.goooler.demoapp.model.Constants
import io.goooler.demoapp.util.DialogManager
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.ToastUtil

/**
 * Activity 基类，封装通用方法
 */
abstract class BaseActivity : AppCompatActivity() {

    val dialogManager by lazy(LazyThreadSafetyMode.NONE) {
        val manager = DialogManager()
        lifecycle.addObserver(manager)
        manager
    }

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

    /**
     * @param containerId       容器 id
     * @param fragment          要添加的 fragment
     * @param isAddToBackStack  将要添加的 fragment 是否要添加到返回栈，默认不添加
     * @param tag               fragment 的 tag
     */
    protected fun addFragment(@IdRes containerId: Int,
                              fragment: Fragment,
                              isAddToBackStack: Boolean = false,
                              tag: String? = null) {
        if (fragment.isAdded) return
        getFragmentTransaction(isAddToBackStack, tag).add(containerId, fragment, tag).commit()
    }

    /**
     * @param containerId       容器 id
     * @param fragment          要替换的 fragment
     * @param isAddToBackStack  将要替换的 fragment 是否要添加到返回栈，默认添加
     * @param tag               fragment 的 tag
     */
    protected fun replaceFragment(@IdRes containerId: Int,
                                  fragment: Fragment,
                                  isAddToBackStack: Boolean = true,
                                  tag: String? = null) {
        if (fragment.isAdded) return
        getFragmentTransaction(isAddToBackStack, tag).replace(containerId, fragment, tag).commit()
    }

    private fun getFragmentTransaction(isAddToBackStack: Boolean, tag: String?) =
            supportFragmentManager.beginTransaction().apply {
                if (isAddToBackStack) {
                    addToBackStack(tag)
                }
            }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProviders.of(this@BaseActivity).get(modelClass).apply {
            lifecycle.addObserver(this)
            toast.observe(this@BaseActivity, Observer {
                showToast(it)
            })
        }
    }

    protected fun showToast(@StringRes textId: Int) {
        ToastUtil.showToast(textId)
    }

    protected fun showToast(text: String) {
        ToastUtil.showToast(text)
    }
}
