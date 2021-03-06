package io.goooler.demoapp.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px

class StatusBarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getStatusBarHeight())
  }

  @Px
  private fun getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
  }
}
