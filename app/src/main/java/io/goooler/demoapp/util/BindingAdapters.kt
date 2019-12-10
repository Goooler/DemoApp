package io.goooler.demoapp.util

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
import androidx.databinding.BindingAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.goooler.demoapp.util.image.ImageLoader
import java.io.File

//------------------------View --------------------------//


@BindingAdapter(value = ["binding_isGone"], requireAll = true)
fun View.bindingIsGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["binding_isVisible"], requireAll = true)
fun View.bindingIsVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(value = ["binding_isSelected"], requireAll = true)
fun View.bindingIsSelect(select: Boolean) {
    isSelected = select
}

@BindingAdapter(value = ["binding_rect_radius"], requireAll = true)
fun View.bindingRectCornerRadius(radius: Float) {
    val p = object : ViewOutlineProvider() {
        override fun getOutline(v: View?, outline: Outline?) {
            if (v == null || outline == null) return
            outline.setRoundRect(0, 0, v.width, v.height, radius)
        }
    }
    outlineProvider = p
    clipToOutline = true
}

@BindingAdapter(value = ["binding_width", "binding_height"], requireAll = true)
fun View.bindingWidthAndHeight(width: Float, height: Float) {
    val p = layoutParams
    p.width = width.toInt()
    p.height = height.toInt()
    requestLayout()
}

@BindingAdapter(value = ["binding_width"], requireAll = true)
fun View.bindingWidth(width: Float) {
    val p = layoutParams
    p.width = width.toInt()
    requestLayout()
}

@BindingAdapter(value = ["binding_height"], requireAll = true)
fun View.bindingHeight(height: Float) {
    val p = layoutParams
    p.height = height.toInt()
    requestLayout()
}

@BindingAdapter(value = ["binding_padding"], requireAll = true)
fun View.bindingPadding(padding: Float) {
    setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
}

@BindingAdapter(value = ["binding_paddingTop"], requireAll = true)
fun View.bindingPaddingTop(topPadding: Float) {
    setPadding(paddingLeft, topPadding.toInt(), paddingRight, paddingBottom)
}

@BindingAdapter(value = ["binding_paddingBottom"], requireAll = true)
fun View.bindingPaddingBottom(bottomPadding: Float) {
    setPadding(paddingLeft, paddingTop, paddingRight, bottomPadding.toInt())
}

@BindingAdapter(value = ["binding_paddingLeft"], requireAll = true)
fun View.bindingPaddingLeft(leftPadding: Float) {
    setPadding(leftPadding.toInt(), paddingTop, paddingRight, paddingBottom)
}

@BindingAdapter(value = ["binding_paddingRight"], requireAll = true)
fun View.bindingPaddingRight(rightPadding: Float) {
    setPadding(paddingLeft, paddingTop, rightPadding.toInt(), paddingBottom)
}

@BindingAdapter(value = ["binding_paddingStart"], requireAll = true)
fun View.bindingPaddingStart(startPadding: Float) {
    setPaddingRelative(startPadding.toInt(), paddingTop, paddingEnd, paddingBottom)
}

@BindingAdapter(value = ["binding_paddingEnd"], requireAll = true)
fun View.bindingPaddingEnd(endPadding: Float) {
    setPaddingRelative(paddingStart, paddingTop, endPadding.toInt(), paddingBottom)
}

@BindingAdapter(value = ["binding_marginTop"], requireAll = true)
fun View.bindingMarginTop(marginTop: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.topMargin = marginTop.toInt()
        layoutParams = p
    }
}

@BindingAdapter(value = ["binding_marginBottom"], requireAll = true)
fun View.bindingMarginBottom(marginBottom: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.bottomMargin = marginBottom.toInt()
        layoutParams = p
    }
}

@BindingAdapter(value = ["binding_marginLeft"], requireAll = true)
fun View.bindingMarginLeft(marginLeft: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.leftMargin = marginLeft.toInt()
        layoutParams = p
    }
}

@BindingAdapter(value = ["binding_marginRight"], requireAll = true)
fun View.bindingMarginRight(marginRight: Float) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.rightMargin = marginRight.toInt()
        layoutParams = p
    }
}


//------------------------View Bg Shape---------------------//


@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_endColor",
    "binding_bg_angle", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt endColor: Int, angle: Int, radius: Float) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, endColor), angle = angle, radius = radius)
}

@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_endColor",
    "binding_bg_angle", "binding_bg_stroke", "binding_bg_strokeColor", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt endColor: Int, angle: Int, stroke: Float, @ColorInt strokeColor: Int, radius: Float) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, endColor), angle = angle, radius = radius, strokeColor = strokeColor, stroke = stroke)
}

@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_centerColor", "binding_bg_endColor",
    "binding_bg_angle", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt centerColor: Int, @ColorInt endColor: Int,
                               angle: Int, radius: Float) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, centerColor, endColor), angle = angle, radius = radius)
}

@BindingAdapter(value = ["binding_bg_solidColor", "binding_bg_topLeftRadius", "binding_bg_topRightRadius", "binding_bg_bottomLeftRadius", "binding_bg_bottomRightRadius"], requireAll = false)
fun View.bindingBgShapeCorners(@ColorInt solidColor: Int, topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
    setBgShapeCorners(solidColor, topLeft, topRight, bottomLeft, bottomRight)
}

@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_endColor", "binding_bg_angle"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt endColor: Int, angle: Int) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, endColor), angle = angle)
}

@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_centerColor", "binding_bg_endColor", "binding_bg_angle"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt centerColor: Int, @ColorInt endColor: Int, angle: Int) {
    setBgShapeGradual(gradualColors = intArrayOf(startColor, centerColor, endColor), angle = angle)
}

@BindingAdapter(value = ["binding_bg_stroke", "binding_bg_strokeColor", "binding_bg_solidColor", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeStroke(stroke: Float, @ColorInt strokeColor: Int, @ColorInt solidColor: Int, radius: Float) {
    setBgShapeGradual(stroke = stroke, strokeColor = strokeColor, solidColor = solidColor, radius = radius)
}

@BindingAdapter(value = ["binding_bg_solidColor", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShape(@ColorInt solidColor: Int, radius: Float) {
    setBgShapeGradual(solidColor = solidColor, radius = radius)
}

@BindingAdapter(value = ["binding_bg_stroke", "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"], requireAll = true)
fun View.bindingBgShapeOvalStroke(stroke: Float, @ColorInt strokeColor: Int, solidOvalColor: Int) {
    setBgShapeGradual(shapeType = GradientDrawable.OVAL, strokeColor = strokeColor, stroke = stroke, solidColor = solidOvalColor)
}

@BindingAdapter(value = ["binding_bg_solidOvalColor"], requireAll = true)
fun View.bindingBgShapeOval(solidOvalColor: Int) {
    setBgShapeGradual(shapeType = GradientDrawable.OVAL, solidColor = solidOvalColor)
}


//------------------------ImageView--------------------------//


@BindingAdapter("binding_src_url")
fun ImageView.bindingImageUrl(url: String?) {
    ImageLoader.loadOss(this, url)
}

@BindingAdapter("binding_src_url_circle")
fun ImageView.bindingCircleImageUrl(url: String?) {
    ImageLoader.loadCircleCropOss(this, url)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingImageUrl(url: String?, placeholder: Drawable?) {
    ImageLoader.loadOss(this, url, placeholder)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_placeholder", "binding_src_error"], requireAll = true)
fun ImageView.bindingImageUrl(url: String?, placeholder: Drawable?, error: Drawable?) {
    ImageLoader.loadOss(this, url, placeholder, error)
}

@BindingAdapter(value = ["binding_src_url_circle", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingCircleImageUrl(url: String?, drawable: Drawable?) {
    ImageLoader.loadCircleCropOss(this, url, drawable)
}

@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingCenterCrop(url: String?, drawable: Drawable?) {
    ImageLoader.loadCenterCropOss(this, url, drawable)
}

@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_placeholder", "binding_src_error"], requireAll = true)
fun ImageView.bindingCenterCrop(url: String?, drawable: Drawable?, error: Drawable?) {
    ImageLoader.loadCenterCropOss(this, url, drawable, error)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_cornerRadius"], requireAll = true)
fun ImageView.bindingRoundedCorner(url: String?, radius: Float) {
    ImageLoader.loadRoundedCornerOss(this, url, radius.toInt())
}

@BindingAdapter(value = ["binding_src_url", "binding_src_cornerRadius", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingRoundedCorner(url: String?, radius: Float, drawable: Drawable?) {
    ImageLoader.loadRoundedCornerOss(this, url, radius.toInt(), drawable)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_cornerRadius", "binding_src_placeholder", "binding_src_error"],
        requireAll = true)
fun ImageView.bindingRoundedCorner(
        url: String?,
        radius: Float,
        drawable: Drawable?,
        error: Drawable?) {
    ImageLoader.loadRoundedCornerOss(this, url, radius.toInt(), drawable, error)
}

@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_cornerRadius"], requireAll = true)
fun ImageView.setRoundedCornerCenterCrop(url: String?, radius: Float) {
    ImageLoader.loadCenterCropRoundedCornerOss(this, url, radius.toInt())
}

@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_cornerRadius",
    "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingRoundedCornerCenterCrop(url: String?, radius: Float, drawable: Drawable?) {
    ImageLoader.loadCenterCropRoundedCornerOss(this, url, radius.toInt(), drawable)
}

@BindingAdapter(value = ["binding_src_url_fixWidth", "binding_src_baseWidth", "binding_src_baseHeight"], requireAll = true)
fun ImageView.bindingImageUrlFixWidth(url: String?, baseWidth: Float, baseHeight: Float) {
    val size = url?.getSizeByLoadUrl(baseWidth.toInt(), baseHeight.toInt()) ?: ArrayList()
    val p = layoutParams
    p.height = baseHeight.toInt()
    p.width = if (size[0] > 0 && size[1] > 0) {
        (baseHeight / size[1] * size[0]).toInt()
    } else {
        baseWidth.toInt()
    }
    requestLayout()
    ImageLoader.loadOss(this, url)
}

@BindingAdapter(value = ["binding_src_url_fixHeight", "binding_src_baseWidth", "binding_src_baseHeight"], requireAll = true)
fun ImageView.bindingImageUrlFixHeight(url: String?, baseWidth: Float, baseHeight: Float) {
    val size = url?.getSizeByLoadUrl(baseWidth.toInt(), baseHeight.toInt()) ?: ArrayList()
    val p = layoutParams
    p.width = baseWidth.toInt()
    p.height = if (size[0] > 0 && size[1] > 0) {
        (baseWidth / size[0] * size[1]).toInt()
    } else {
        baseHeight.toInt()
    }
    requestLayout()
    ImageLoader.loadOss(this, url)
}

@BindingAdapter("binding_src_file")
fun ImageView.bindingFileToImage(file: File?) {
    if (file != null) {
        val uri = Uri.fromFile(file)
        setImageURI(uri)
    }
}

@BindingAdapter("binding_src_filePath")
fun ImageView.bindingFileToImageFromPath(path: String?) {
    if (path != null) {
        val uri = Uri.parse(path)
        setImageURI(uri)
    }
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
    val tf: Typeface = Typeface.createFromAsset(context.assets, path)
    typeface = tf
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

@BindingAdapter("binding_textColor")
fun TextView.bindingTextColor(@ColorInt color: Int) {
    setTextColor(color)
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

@BindingAdapter("binding_srl_close_animation")
fun SmartRefreshLayout.bindingCloseAnimation(isColse: Boolean) {
    if (isColse) {
        //关闭下拉动画特效，减少延迟感觉
        setReboundDuration(0)
        finishRefresh(0)
    }
}

@BindingAdapter("binding_srl_onLoadMoreListener")
fun SmartRefreshLayout.bindingOnLoadMoreListener(listener: OnLoadMoreListener) {
    setOnLoadMoreListener(listener)
}

@BindingAdapter("binding_srl_isRefreshFinish")
fun SmartRefreshLayout.bindingIsRefreshFinish(isFinish: Boolean) {
    if (isFinish) {
        finishRefresh()
    }
}

@BindingAdapter("binding_srl_isLoadMoreFinish")
fun SmartRefreshLayout.bindingIsLoadMoreFinish(isFinish: Boolean) {
    if (isFinish) {
        finishLoadMore()
    }
}

@BindingAdapter("binding_srl_isNoMore")
fun SmartRefreshLayout.bindingNoMoreData(noMore: Boolean) {
    setNoMoreData(noMore)
}

@BindingAdapter("binding_srl_isEnableLoadMore")
fun SmartRefreshLayout.bindingIsEnableLoadMore(enable: Boolean) {
    setEnableLoadMore(enable)
}

@BindingAdapter("binding_srl_isEnableRefresh")
fun SmartRefreshLayout.bindingIsEnableRefresh(enable: Boolean) {
    setEnableRefresh(enable)
}

@BindingAdapter("binding_srl_isHeaderEmpty")
fun SmartRefreshLayout.bindingHeaderEmpty(isEmpty: Boolean) {
    val header = refreshHeader
    if (header is ClassicsHeader) {
        for (i in 0 until header.childCount) {
            val child = header.getChildAt(i)
            child.alpha = if (isEmpty) {
                0f
            } else {
                1f
            }
        }
    }
}

@BindingAdapter("binding_srl_isFooterEmpty")
fun SmartRefreshLayout.bindingFooterEmpty(isEmpty: Boolean) {
    val footer = refreshFooter
    if (footer is ClassicsFooter) {
        for (i in 0 until footer.childCount) {
            val child = footer.getChildAt(i)
            child.alpha = if (isEmpty) {
                0f
            } else {
                1f
            }
        }
    }
}

@BindingAdapter("binding_srl_headerPrimaryColor")
fun SmartRefreshLayout.bindingHeaderPrimaryColor(@ColorInt color: Int) {
    val header = refreshHeader
    if (header == null) {
        setRefreshHeader(ClassicsHeader(context).apply { setPrimaryColor(color) })
    } else {
        if (header is ClassicsHeader) {
            header.setPrimaryColor(color)
        }
    }
}

@BindingAdapter("binding_srl_footerPrimaryColor")
fun SmartRefreshLayout.bindingFooterPrimaryColor(@ColorInt color: Int) {
    val footer = refreshFooter
    if (footer == null) {
        setRefreshFooter(ClassicsFooter(context).apply { setPrimaryColor(color) })
    } else {
        if (footer is ClassicsFooter) {
            footer.setPrimaryColor(color)
        }
    }
}

@BindingAdapter("binding_srl_headerAccentColor")
fun SmartRefreshLayout.bindingHeaderAccentColor(@ColorInt color: Int) {
    val header = refreshHeader
    if (header == null) {
        setRefreshHeader(ClassicsHeader(context).apply { setAccentColor(color) })
    } else {
        if (header is ClassicsHeader) {
            header.setAccentColor(color)
        }
    }
}

@BindingAdapter("binding_srl_footerAccentColor")
fun SmartRefreshLayout.bindingFooterAccentColor(@ColorInt color: Int) {
    val footer = refreshFooter
    if (footer == null) {
        setRefreshFooter(ClassicsFooter(context).apply { setAccentColor(color) })
    } else {
        if (footer is ClassicsFooter) {
            footer.setAccentColor(color)
        }
    }
}