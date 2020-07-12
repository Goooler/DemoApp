package io.goooler.demoapp.base.util

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*

import io.goooler.demoapp.base.core.BaseApplication


/**
 * 获取资源的工具封装，可以在任何控件中直接获取
 */
@SuppressLint("NewApi")
object ResUtil {

    private val resources: Resources = BaseApplication.context.resources

    fun getString(@StringRes id: Int): String {
        return resources.getString(id)
    }

    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return resources.getStringArray(id)
    }

    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return resources.getColor(id, null)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable {
        return resources.getDrawable(id, null)
    }
}
