package io.goooler.demoapp.base

import android.os.Bundle

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

import io.goooler.demoapp.model.Constants
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.ToastUtil

/**
 * 基类，封装通用方法
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
}
