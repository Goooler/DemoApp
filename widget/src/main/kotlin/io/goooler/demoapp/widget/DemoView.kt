package io.goooler.demoapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DemoView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val rectF = RectF()
  private var widthSize = 0
  private var heightSize = 0

  init {
    paint.run {
      color = Color.GRAY
      style = Paint.Style.FILL
      strokeWidth = 10f
    }
  }

  /**
   * 测量大小
   */
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    widthSize = MeasureSpec.getSize(widthMeasureSpec)
    heightSize = MeasureSpec.getSize(heightMeasureSpec)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    rectF.set(20f, 20f, widthSize - 20f, heightSize - 20f)
    canvas.run {
      drawLine(20f, 20f, widthSize - 20f, heightSize - 20f, paint)
      drawRoundRect(rectF, 30f, 30f, paint)
    }
  }
}
