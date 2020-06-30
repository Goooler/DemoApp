package io.goooler.demoapp.base

import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.goooler.demoapp.util.DialogManager
import io.goooler.demoapp.util.ToastUtil
import io.goooler.demoapp.util.device.AdaptScreenUtil
import io.goooler.demoapp.util.unsafeLazy

/**
 * Activity 基类，封装通用方法
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val dialogManager by unsafeLazy {
        val manager = DialogManager()
        lifecycle.addObserver(manager)
        manager
    }

    protected val originalResources by unsafeLazy { super.getResources() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.run {
            setBackgroundDrawable(null)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
        // activity 入栈 List<Activity> 记录
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        // activity 出栈 List<Activity> 移除
        ActivityCollector.removeActivity(this)
        super.onDestroy()
    }

    override fun getResources(): Resources {
        return AdaptScreenUtil.adaptWidth(originalResources, 360)
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要添加的 fragment
     * @param isAddToBackStack  将要添加的 fragment 是否要添加到返回栈，默认不添加
     * @param tag               fragment 的 tag
     */
    protected fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = false,
        tag: String? = null
    ) {
        if (fragment.isAdded) return
        supportFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            add(containerViewId, fragment, tag)
        }
    }

    /**
     * @param containerViewId   容器 id
     * @param fragment          要替换的 fragment
     * @param isAddToBackStack  将要替换的 fragment 是否要添加到返回栈，默认添加
     * @param tag               fragment 的 tag
     */
    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        isAddToBackStack: Boolean = true,
        tag: String? = null
    ) {
        if (fragment.isAdded) return
        supportFragmentManager.commit {
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            replace(containerViewId, fragment, tag)
        }
    }

    protected fun <T : BaseViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass).apply {
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
