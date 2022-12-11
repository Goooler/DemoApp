package io.goooler.demoapp.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Px

class StatusBarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

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
