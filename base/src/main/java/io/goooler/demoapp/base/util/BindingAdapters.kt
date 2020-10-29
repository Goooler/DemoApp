@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import java.io.File

//------------------------View --------------------------//

@BindingAdapter("binding_isGone")
fun View.bindingIsGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("binding_isVisible")
fun View.bindingIsVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("binding_isSelected")
fun View.bindingIsSelect(select: Boolean) {
    isSelected = select
}

@BindingAdapter("binding_rect_radius")
fun View.bindingRectCornerRadius(@Px radius: Float) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(v: View?, outline: Outline?) {
            if (v != null && outline != null) {
                outline.setRoundRect(0, 0, v.width, v.height, radius)
            }
        }
    }
    clipToOutline = true
}

@BindingAdapter("binding_width", "binding_height")
fun View.bindingWidthAndHeight(@Px width: Float, @Px height: Float) {
    layoutParams.width = width.toInt()
    layoutParams.height = height.toInt()
    requestLayout()
}

@BindingAdapter("binding_width")
fun View.bindingWidth(@Px width: Float) {
    layoutParams.width = width.toInt()
    requestLayout()
}

@BindingAdapter("binding_height")
fun View.bindingHeight(@Px height: Float) {
    layoutParams.height = height.toInt()
    requestLayout()
}

@BindingAdapter("binding_marginTop")
fun View.bindingMarginTop(@Px marginTop: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.topMargin = marginTop.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginBottom")
fun View.bindingMarginBottom(@Px marginBottom: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.bottomMargin = marginBottom.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginStart")
fun View.bindingMarginStart(@Px marginLeft: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.marginStart = marginLeft.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginEnd")
fun View.bindingMarginEnd(@Px marginRight: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.marginEnd = marginRight.toInt()
        layoutParams = p
    }
}

//------------------------View Bg Shape---------------------//

@BindingAdapter(
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_radius"
)
fun View.bindingBgShapeGradual(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    angle: Int,
    @Px radius: Float
) {
    setBgShapeGradual(
        gradualColors = intArrayOf(startColor, endColor),
        angle = angle,
        radius = radius
    )
}

@BindingAdapter(
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_radius"
)
fun View.bindingBgShapeGradual(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    angle: Int,
    @Px stroke: Float,
    @ColorInt strokeColor: Int,
    @Px radius: Float
) {
    setBgShapeGradual(
        gradualColors = intArrayOf(startColor, endColor),
        angle = angle,
        radius = radius,
        strokeColor = strokeColor,
        stroke = stroke
    )
}

@BindingAdapter(
    "binding_bg_startColor",
    "binding_bg_centerColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_radius"
)
fun View.bindingBgShapeGradual(
    @ColorInt startColor: Int,
    @ColorInt centerColor: Int,
    @ColorInt endColor: Int,
    angle: Int,
    @Px radius: Float
) {
    setBgShapeGradual(
        gradualColors = intArrayOf(startColor, centerColor, endColor),
        angle = angle,
        radius = radius
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
        "binding_bg_bottomRightRadius"
    ],
    requireAll = false
)
fun View.bindingBgShapeCorners(
    @ColorInt solidColor: Int,
    @Px topLeft: Float,
    @Px topRight: Float,
    @Px bottomLeft: Float,
    @Px bottomRight: Float
) {
    setBgShapeCorners(solidColor, topLeft, topRight, bottomLeft, bottomRight)
}

@BindingAdapter(
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle"
)
fun View.bindingBgShapeGradual(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    angle: Int
) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, endColor), angle = angle)
}

@BindingAdapter(
    "binding_bg_startColor",
    "binding_bg_centerColor",
    "binding_bg_endColor",
    "binding_bg_angle"
)
fun View.bindingBgShapeGradual(
    @ColorInt startColor: Int,
    @ColorInt centerColor: Int,
    @ColorInt endColor: Int,
    angle: Int
) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, centerColor, endColor), angle = angle)
}

@BindingAdapter(
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidColor",
    "binding_bg_radius"
)
fun View.bindingBgShapeStroke(
    @Px stroke: Float,
    @ColorInt strokeColor: Int,
    @ColorInt solidColor: Int,
    @Px radius: Float
) {
    setBgShapeGradual(
        stroke = stroke,
        strokeColor = strokeColor,
        solidColor = solidColor,
        radius = radius
    )
}

@BindingAdapter(
    "binding_bg_solidColor",
    "binding_bg_radius"
)
fun View.bindingBgShape(
    @ColorInt solidColor: Int,
    @Px radius: Float
) {
    setBgShapeGradual(solidColor = solidColor, radius = radius)
}

@BindingAdapter(
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"
)
fun View.bindingBgShapeOvalStroke(
    @Px stroke: Float,
    @ColorInt strokeColor: Int,
    @ColorInt solidOvalColor: Int
) {
    setBgShapeGradual(
        shapeType = GradientDrawable.OVAL,
        strokeColor = strokeColor,
        stroke = stroke,
        solidColor = solidOvalColor
    )
}

@BindingAdapter("binding_bg_solidOvalColor")
fun View.bindingBgShapeOval(@ColorInt solidOvalColor: Int) {
    setBgShapeGradual(shapeType = GradientDrawable.OVAL, solidColor = solidOvalColor)
}

//------------------------ImageView--------------------------//

@BindingAdapter("binding_src_file")
fun ImageView.bindingFileToImage(file: File) {
    setImageURI(Uri.fromFile(file))
}

@BindingAdapter("binding_src_filePath")
fun ImageView.bindingFileToImageFromPath(path: String) {
    setImageURI(Uri.parse(path))
}

@BindingAdapter("binding_img_res")
fun ImageView.bindingImageResource(@DrawableRes res: Int) {
    setImageResource(res)
}

@BindingAdapter("binding_src_drawable")
fun ImageView.bindingImageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}

//------------------------TextView--------------------------//

@BindingAdapter("binding_font_type")
fun TextView.bindingImpactTypeface(path: String) {
    typeface = Typeface.createFromAsset(context.assets, path)
}

@BindingAdapter("binding_paint_flag")
fun TextView.bindingPaintFlag(flag: Int) {
    paint.flags = flag
    paint.isAntiAlias = true
}

@BindingAdapter("binding_paint_flag_is_thru")
fun TextView.bindingPaintFlagThru(flag: Boolean) {
    if (flag) {
        paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        paint.isAntiAlias = true
    } else {
        paint.flags = 0
    }
}

//---------------------View-------------------------------//

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
    @Px radius: Float = 0f
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
    @Px bottomRight: Float = 0f
) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(solidColor)
        cornerRadii = floatArrayOf(
            topLeft, topLeft,
            topRight, topRight,
            bottomRight, bottomRight,
            bottomLeft, bottomLeft
        )
    }
}