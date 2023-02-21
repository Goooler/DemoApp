package io.goooler.demoapp.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.res.use

class StatusBarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
  private val height: Int

  init {
    context.obtainStyledAttributes(
      attrs,
      androidx.appcompat.R.styleable.View
    ).use {
      height = it.getDimension(androidx.appcompat.R.styleable.ActionBar_height, 0F).toInt()
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getStatusBarHeight())
  }

  @Px
  private fun getStatusBarHeight(): Int {
    val resources = Resources.getSystem()

    @SuppressLint("InternalInsetResource")
    @DimenRes
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resId)
  }
}
