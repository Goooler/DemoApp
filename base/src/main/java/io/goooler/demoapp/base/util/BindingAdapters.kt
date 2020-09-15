@file:Suppress("unused")

package io.goooler.demoapp.base.util

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
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
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
fun View.bindingRectCornerRadius(radius: Float) {
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
fun View.bindingWidthAndHeight(width: Float, height: Float) {
    layoutParams.width = width.toInt()
    layoutParams.height = height.toInt()
    requestLayout()
}

@BindingAdapter("binding_width")
fun View.bindingWidth(width: Float) {
    layoutParams.width = width.toInt()
    requestLayout()
}

@BindingAdapter("binding_height")
fun View.bindingHeight(height: Float) {
    layoutParams.height = height.toInt()
    requestLayout()
}

@BindingAdapter("binding_marginTop")
fun View.bindingMarginTop(marginTop: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.topMargin = marginTop.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginBottom")
fun View.bindingMarginBottom(marginBottom: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.bottomMargin = marginBottom.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginStart")
fun View.bindingMarginStart(marginLeft: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.marginStart = marginLeft.toInt()
        layoutParams = p
    }
}

@BindingAdapter("binding_marginEnd")
fun View.bindingMarginEnd(marginRight: Float) {
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
    radius: Float
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
    stroke: Float,
    @ColorInt strokeColor: Int,
    radius: Float
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
    radius: Float
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
    value = ["binding_bg_solidColor",
        "binding_bg_topLeftRadius",
        "binding_bg_topRightRadius",
        "binding_bg_bottomLeftRadius",
        "binding_bg_bottomRightRadius"],
    requireAll = false
)
fun View.bindingBgShapeCorners(
    @ColorInt solidColor: Int,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float
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
    stroke: Float,
    @ColorInt strokeColor: Int,
    @ColorInt solidColor: Int,
    radius: Float
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
    radius: Float
) {
    setBgShapeGradual(solidColor = solidColor, radius = radius)
}

@BindingAdapter(
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"
)
fun View.bindingBgShapeOvalStroke(
    stroke: Float,
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
fun View.bindingBgShapeOval(solidOvalColor: Int) {
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
fun ImageView.bindingImageResource(res: Int) {
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

//------------------------SmartRefreshLayout--------------------------//

@BindingAdapter("binding_srl_onRefreshLoadMoreListener")
fun SmartRefreshLayout.bindingOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener) {
    setOnRefreshLoadMoreListener(listener)
}

@BindingAdapter("binding_srl_onRefreshListener")
fun SmartRefreshLayout.bindingOnRefreshListener(listener: OnRefreshListener) {
    setOnRefreshListener(listener)
}

@BindingAdapter("binding_srl_onLoadMoreListener")
fun SmartRefreshLayout.bindingOnLoadMoreListener(listener: OnLoadMoreListener) {
    setOnLoadMoreListener(listener)
}

@BindingAdapter("binding_srl_close_animation")
fun SmartRefreshLayout.bindingCloseAnimation(close: Boolean) {
    if (close) {
        //关闭下拉动画特效，减少延迟感觉
        setReboundDuration(0)
        finishRefresh(0)
    }
}

@BindingAdapter("binding_srl_refreshFinish")
fun SmartRefreshLayout.bindingRefreshFinish(finish: Boolean) {
    if (finish) {
        finishRefresh()
    }
}

@BindingAdapter("binding_srl_isLoadMoreFinish")
fun SmartRefreshLayout.bindingLoadMoreFinish(finish: Boolean) {
    if (finish) {
        finishLoadMore()
    }
}

@BindingAdapter("binding_srl_isNoMore")
fun SmartRefreshLayout.bindingNoMoreData(noMore: Boolean) {
    setNoMoreData(noMore)
}

@BindingAdapter("binding_srl_isEnableLoadMore")
fun SmartRefreshLayout.bindingEnableLoadMore(enable: Boolean) {
    setEnableLoadMore(enable)
}

@BindingAdapter("binding_srl_isEnableRefresh")
fun SmartRefreshLayout.bindingEnableRefresh(enable: Boolean) {
    setEnableRefresh(enable)
}

@BindingAdapter("binding_srl_isHeaderEmpty")
fun SmartRefreshLayout.bindingHeaderEmpty(isEmpty: Boolean) {
    (refreshHeader as? ClassicsHeader)?.forEach {
        it.alpha = if (isEmpty) {
            0f
        } else {
            1f
        }
    }
}

@BindingAdapter("binding_srl_isFooterEmpty")
fun SmartRefreshLayout.bindingFooterEmpty(isEmpty: Boolean) {
    (refreshFooter as? ClassicsFooter)?.forEach {
        it.alpha = if (isEmpty) {
            0f
        } else {
            1f
        }
    }
}

@BindingAdapter("binding_srl_headerPrimaryColor")
fun SmartRefreshLayout.bindingHeaderPrimaryColor(@ColorInt color: Int) {
    if (refreshHeader == null) {
        setRefreshHeader(ClassicsHeader(context).apply { setPrimaryColor(color) })
    } else {
        (refreshHeader as? ClassicsHeader)?.setPrimaryColor(color)
    }
}

@BindingAdapter("binding_srl_footerPrimaryColor")
fun SmartRefreshLayout.bindingFooterPrimaryColor(@ColorInt color: Int) {
    if (refreshFooter == null) {
        setRefreshFooter(ClassicsFooter(context).apply { setPrimaryColor(color) })
    } else {
        (refreshFooter as? ClassicsFooter)?.setPrimaryColor(color)
    }
}

@BindingAdapter("binding_srl_headerAccentColor")
fun SmartRefreshLayout.bindingHeaderAccentColor(@ColorInt color: Int) {

    if (refreshHeader == null) {
        setRefreshHeader(ClassicsHeader(context).apply { setAccentColor(color) })
    } else {
        (refreshHeader as? ClassicsHeader)?.setAccentColor(color)
    }
}

@BindingAdapter("binding_srl_footerAccentColor")
fun SmartRefreshLayout.bindingFooterAccentColor(@ColorInt color: Int) {
    if (refreshFooter == null) {
        setRefreshFooter(ClassicsFooter(context).apply { setAccentColor(color) })
    } else {
        (refreshFooter as? ClassicsFooter)?.setAccentColor(color)
    }
}