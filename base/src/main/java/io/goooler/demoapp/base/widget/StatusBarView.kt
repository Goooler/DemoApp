package io.goooler.demoapp.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils

/**
 * 为了适配沉浸式状态栏的自定义状态栏，高度适配刘海屏
 */
class StatusBarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width, BarUtils.getStatusBarHeight())
    }
}