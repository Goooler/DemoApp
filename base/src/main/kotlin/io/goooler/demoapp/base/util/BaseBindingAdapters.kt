@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.databinding.BindingAdapter

// ------------------------View --------------------------//
@BindingAdapter("binding_isEnabled")
internal fun View.bindingIsEnabled(enabled: Boolean) {
  this.isEnabled = enabled
}

@BindingAdapter("binding_isGone")
internal fun View.bindingIsGone(gone: Boolean) {
  visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("binding_isVisible")
internal fun View.bindingIsVisible(show: Boolean) {
  visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("binding_isSelected")
internal fun View.bindingIsSelect(select: Boolean) {
  isSelected = select
}

@BindingAdapter("binding_rect_radius")
internal fun View.bindingRectCornerRadius(@Px radius: Float) {
  outlineProvider = object : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
      outline.setRoundRect(0, 0, view.width, view.height, radius)
    }
  }
  clipToOutline = true
}

@BindingAdapter("binding_width", "binding_height")
internal fun View.bindingWidthAndHeight(@Px width: Float, @Px height: Float) {
  layoutParams.width = width.toInt()
  layoutParams.height = height.toInt()
  requestLayout()
}

@BindingAdapter("binding_width")
internal fun View.bindingWidth(@Px width: Float) {
  layoutParams.width = width.toInt()
  requestLayout()
}

@BindingAdapter("binding_height")
internal fun View.bindingHeight(@Px height: Float) {
  layoutParams.height = height.toInt()
  requestLayout()
}

@BindingAdapter("binding_marginTop")
internal fun View.bindingMarginTop(@Px margin: Float) {
  marginDirection(1, margin)
}

@BindingAdapter("binding_marginBottom")
internal fun View.bindingMarginBottom(@Px margin: Float) {
  marginDirection(3, margin)
}

@BindingAdapter("binding_marginStart")
internal fun View.bindingMarginStart(@Px margin: Float) {
  marginDirection(0, margin)
}

@BindingAdapter("binding_marginEnd")
internal fun View.bindingMarginEnd(@Px margin: Float) {
  marginDirection(2, margin)
}

@BindingAdapter("binding_onLongClick")
internal fun View.bindingOnLongClick(body: () -> Unit) {
  setOnLongClickListener {
    body()
    true
  }
}

@BindingAdapter("binding_tint")
fun ImageView.bindingTint(@ColorInt color: Int) {
  setColorFilter(color, PorterDuff.Mode.SRC_IN)
}

// ------------------------View Bg Shape---------------------//

@BindingAdapter(
  "binding_bg_startColor",
  "binding_bg_endColor",
  "binding_bg_angle",
  "binding_bg_radius",
)
internal fun View.bindingBgShapeGradual(
  @ColorInt startColor: Int,
  @ColorInt endColor: Int,
  angle: Int,
  @Px radius: Float,
) {
  setBgShapeGradual(
    gradualColors = intArrayOf(startColor, endColor),
    angle = angle,
    radius = radius,
  )
}

@BindingAdapter(
  "binding_bg_startColor",
  "binding_bg_endColor",
  "binding_bg_angle",
  "binding_bg_stroke",
  "binding_bg_strokeColor",
  "binding_bg_radius",
)
internal fun View.bindingBgShapeGradual(
  @ColorInt startColor: Int,
  @ColorInt endColor: Int,
  angle: Int,
  @Px stroke: Float,
  @ColorInt strokeColor: Int,
  @Px radius: Float,
) {
  setBgShapeGradual(
    gradualColors = intArrayOf(startColor, endColor),
    angle = angle,
    radius = radius,
    strokeColor = strokeColor,
    stroke = stroke,
  )
}

@BindingAdapter(
  "binding_bg_startColor",
  "binding_bg_centerColor",
  "binding_bg_endColor",
  "binding_bg_angle",
  "binding_bg_radius",
)
internal fun View.bindingBgShapeGradual(
  @ColorInt startColor: Int,
  @ColorInt centerColor: Int,
  @ColorInt endColor: Int,
  angle: Int,
  @Px radius: Float,
) {
  setBgShapeGradual(
    gradualColors = intArrayOf(startColor, centerColor, endColor),
    angle = angle,
    radius = radius,
  )
}

/**
 * 这个比较特殊，requireAll = false
 */
@BindingAdapter(
  value = [
    "binding_bg_solidColor",
    "binding_bg_topLeftRadius",
    "binding_bg_topRightRadius",
    "binding_bg_bottomLeftRadius",
    "binding_bg_bottomRightRadius",
  ],
  requireAll = false,
)
internal fun View.bindingBgShapeCorners(
  @ColorInt solidColor: Int,
  @Px topLeft: Float,
  @Px topRight: Float,
  @Px bottomLeft: Float,
  @Px bottomRight: Float,
) {
  setBgShapeCorners(solidColor, topLeft, topRight, bottomLeft, bottomRight)
}

@BindingAdapter(
  "binding_bg_startColor",
  "binding_bg_endColor",
  "binding_bg_angle",
)
internal fun View.bindingBgShapeGradual(
  @ColorInt startColor: Int,
  @ColorInt endColor: Int,
  angle: Int,
) {
  setBgShapeGradual(gradualColors = intArrayOf(startColor, endColor), angle = angle)
}

@BindingAdapter(
  "binding_bg_startColor",
  "binding_bg_centerColor",
  "binding_bg_endColor",
  "binding_bg_angle",
)
internal fun View.bindingBgShapeGradual(
  @ColorInt startColor: Int,
  @ColorInt centerColor: Int,
  @ColorInt endColor: Int,
  angle: Int,
) {
  setBgShapeGradual(gradualColors = intArrayOf(startColor, centerColor, endColor), angle = angle)
}

@BindingAdapter(
  "binding_bg_stroke",
  "binding_bg_strokeColor",
  "binding_bg_solidColor",
  "binding_bg_radius",
)
internal fun View.bindingBgShapeStroke(
  @Px stroke: Float,
  @ColorInt strokeColor: Int,
  @ColorInt solidColor: Int,
  @Px radius: Float,
) {
  setBgShapeGradual(
    stroke = stroke,
    strokeColor = strokeColor,
    solidColor = solidColor,
    radius = radius,
  )
}

@BindingAdapter(
  "binding_bg_solidColor",
  "binding_bg_radius",
)
internal fun View.bindingBgShape(
  @ColorInt solidColor: Int,
  @Px radius: Float,
) {
  setBgShapeGradual(solidColor = solidColor, radius = radius)
}

@BindingAdapter(
  "binding_bg_stroke",
  "binding_bg_strokeColor",
  "binding_bg_solidOvalColor",
)
internal fun View.bindingBgShapeOvalStroke(
  @Px stroke: Float,
  @ColorInt strokeColor: Int,
  @ColorInt solidOvalColor: Int,
) {
  setBgShapeGradual(
    shapeType = GradientDrawable.OVAL,
    strokeColor = strokeColor,
    stroke = stroke,
    solidColor = solidOvalColor,
  )
}

@BindingAdapter("binding_bg_solidOvalColor")
internal fun View.bindingBgShapeOval(@ColorInt solidOvalColor: Int) {
  setBgShapeGradual(shapeType = GradientDrawable.OVAL, solidColor = solidOvalColor)
}

// ------------------------TextView--------------------------//

@BindingAdapter("binding_font_type")
internal fun TextView.bindingImpactTypeface(path: String) {
  typeface = Typeface.createFromAsset(context.assets, path)
}

@BindingAdapter("binding_paint_flag")
internal fun TextView.bindingPaintFlag(flag: Int) {
  paint.flags = flag
  paint.isAntiAlias = true
}

@BindingAdapter("binding_paint_flag_is_thru")
internal fun TextView.bindingPaintFlagThru(flag: Boolean) {
  if (flag) {
    paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
    paint.isAntiAlias = true
  } else {
    paint.flags = 0
  }
}

// ---------------------Private-------------------------------//

/**
 * 设置 view 的背景，支持圆形和矩形，渐变色和描边圆角等
 *
 * @param shapeType 背景形状，圆、矩形等
 * @param gradualColors 渐变色数组，和填充色互斥
 * @param angle 渐变色角度
 * @param solidColor 填充色，和渐变色数组互斥
 * @param strokeColor 描边色
 * @param stroke 描边粗细
 * @param radius 圆角大小
 */
private fun View.setBgShapeGradual(
  shapeType: Int = GradientDrawable.RECTANGLE,
  @ColorInt gradualColors: IntArray? = null,
  angle: Int = 0,
  @ColorInt solidColor: Int? = null,
  @ColorInt strokeColor: Int = Color.TRANSPARENT,
  @Px stroke: Float = 0f,
  @Px radius: Float = 0f,
) {
  background = GradientDrawable().apply {
    shape = shapeType
    useLevel = false
    gradientType = GradientDrawable.LINEAR_GRADIENT
    val remainder = angle % 45
    val validAngle = if (remainder >= 22.5) {
      angle % 360 + 45 - remainder
    } else {
      angle % 360 - remainder
    }
    orientation = when (validAngle) {
      45 -> GradientDrawable.Orientation.BL_TR
      90 -> GradientDrawable.Orientation.BOTTOM_TOP
      135 -> GradientDrawable.Orientation.BR_TL
      180 -> GradientDrawable.Orientation.RIGHT_LEFT
      225 -> GradientDrawable.Orientation.TR_BL
      270 -> GradientDrawable.Orientation.TOP_BOTTOM
      315 -> GradientDrawable.Orientation.TL_BR
      else -> GradientDrawable.Orientation.LEFT_RIGHT
    }
    if (gradualColors != null && solidColor == null) {
      colors = gradualColors
    } else if (gradualColors == null && solidColor != null) {
      setColor(solidColor)
    }
    setStroke(stroke.toInt(), strokeColor)
    cornerRadius = radius
  }
}

/**
 * 设置 view 在矩形某几个角上需要圆角的背景
 *
 * @param solidColor 填充色
 * @param topLeft 左上圆角大小
 * @param topRight 右上圆角大小
 * @param bottomLeft 左下圆角大小
 * @param bottomRight 左下圆角大小
 */
private fun View.setBgShapeCorners(
  @ColorInt solidColor: Int = Color.WHITE,
  @Px topLeft: Float = 0f,
  @Px topRight: Float = 0f,
  @Px bottomLeft: Float = 0f,
  @Px bottomRight: Float = 0f,
) {
  background = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    setColor(solidColor)
    cornerRadii = floatArrayOf(
      topLeft,
      topLeft,
      topRight,
      topRight,
      bottomRight,
      bottomRight,
      bottomLeft,
      bottomLeft,
    )
  }
}

private fun View.marginDirection(direction: Int, @Px margin: Float) {
  if (layoutParams is ViewGroup.MarginLayoutParams) {
    val p = layoutParams as ViewGroup.MarginLayoutParams
    when (direction) {
      0 -> p.marginStart = margin.toInt()
      1 -> p.topMargin = margin.toInt()
      2 -> p.marginEnd = margin.toInt()
      else -> p.bottomMargin = margin.toInt()
    }
    layoutParams = p
  }
}
