package io.goooler.demoapp.util

import android.annotation.SuppressLint
import android.content.res.Resources

import io.goooler.demoapp.base.BaseApplication


/**
 * 获取资源的工具封装，可以在任何控件中直接获取
 */
object ResUtil {

    private val resources: Resources
        get() = BaseApplication.context!!.resources

    fun getString(id: Int): String {
        return resources.getString(id)
    }

    fun getStringArray(id: Int): Array<String> {
        return resources.getStringArray(id)
    }

    @SuppressLint("NewApi")
    fun getColor(id: Int): Int {
        return resources.getColor(id, null)
    }
}
