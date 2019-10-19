package io.goooler.demoapp.base

import android.app.Activity
import io.goooler.demoapp.model.Constants
import io.goooler.demoapp.util.LogUtil
import java.util.*

/**
 * 方便管理 activity
 */
object ActivityCollector {
    private val activityList: MutableList<Activity> = ArrayList()

    /**
     * activity 入栈时记录指针
     */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * activity 出栈时删除指针
     */
    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 提供给外部一个方法直接干掉所有的 activity
     */
    @Suppress("unused")
    fun finishAll() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activityList.clear()
        LogUtil.d(Constants.BASE_ACTIVITY, Constants.FINISH_ALL_ACTIVITY)
    }
}