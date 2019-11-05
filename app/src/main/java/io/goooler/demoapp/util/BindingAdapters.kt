package io.goooler.demoapp.util

import android.graphics.Bitmap
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
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.goooler.demoapp.util.image.ImageLoader
import io.goooler.demoapp.util.image.glide.GlideApp
import java.io.File


/**
 * Created by JokerWan on 2019-05-15.
 * Fixed by feling on 2019-09-25.
 * Function: BindingAdapter
 */

//------------------------View --------------------------//

@BindingAdapter(value = ["binding_isGone"], requireAll = true)
fun View.bindingIsGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["binding_isVisible"], requireAll = true)
fun View.bindingIsVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(value = ["binding_rect_radius"], requireAll = true)
fun View.bindingRectCornerRadius(radius: Float) {
    val p = object : ViewOutlineProvider() {
        override fun getOutline(v: View?, outline: Outline?) {
            if (v == null || outline == null) return
            outline.setRoundRect(0, 0, v.width, v.height, radius)
        }
    }
    this.outlineProvider = p
    this.clipToOutline = true
}

@BindingAdapter(value = ["binding_width", "binding_height"], requireAll = true)
fun View.bindingWidthAndHeight(width: Float, height: Float) {
    val p = this.layoutParams
    p.width = width.toInt()
    p.height = height.toInt()
    this.requestLayout()
}


@BindingAdapter(value = ["binding_width"], requireAll = true)
fun View.bindingWidth(width: Float) {
    val p = this.layoutParams
    p.width = width.toInt()
    this.requestLayout()
}

@BindingAdapter(value = ["binding_height"], requireAll = true)
fun View.bindingHeight(height: Float) {
    val p = this.layoutParams
    p.height = height.toInt()
    this.requestLayout()
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
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.topMargin = marginTop.toInt()
        this.layoutParams = p
    }
}

@BindingAdapter(value = ["binding_marginBottom"], requireAll = true)
fun View.bindingMarginBottom(marginBottom: Float) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.bottomMargin = marginBottom.toInt()
        this.layoutParams = p
    }
}


@BindingAdapter(value = ["binding_marginLeft"], requireAll = true)
fun View.bindingMarginLeft(marginLeft: Float) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.leftMargin = marginLeft.toInt()
        this.layoutParams = p
    }
}

@BindingAdapter(value = ["binding_marginRight"], requireAll = true)
fun View.bindingMarginRight(marginRight: Float) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.rightMargin = marginRight.toInt()
        this.layoutParams = p
    }
}

//------------------------View Bg Shape---------------------//


@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_endColor",
    "binding_bg_angle", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt endColor: Int, angle: Int, radius: Float) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        useLevel = false
        gradientType = GradientDrawable.LINEAR_GRADIENT
        when (angle) {
            0 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
            45 -> orientation = GradientDrawable.Orientation.BL_TR
            90 -> orientation = GradientDrawable.Orientation.BOTTOM_TOP
            135 -> orientation = GradientDrawable.Orientation.BR_TL
            180 -> orientation = GradientDrawable.Orientation.RIGHT_LEFT
            225 -> orientation = GradientDrawable.Orientation.TR_BL
            270 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
            315 -> orientation = GradientDrawable.Orientation.TL_BR
        }
        colors = intArrayOf(startColor, endColor)
        cornerRadius = radius
    }
}


@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_endColor", "binding_bg_angle"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt endColor: Int, angle: Int) {
    this.bindingBgShapeGradual(startColor, endColor, angle, 0f)
}


@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_centerColor", "binding_bg_endColor",
    "binding_bg_angle", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt centerColor: Int, @ColorInt endColor: Int,
                               angle: Int, radius: Float) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        useLevel = false
        gradientType = GradientDrawable.LINEAR_GRADIENT
        when (angle) {
            0 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
            45 -> orientation = GradientDrawable.Orientation.BL_TR
            90 -> orientation = GradientDrawable.Orientation.BOTTOM_TOP
            135 -> orientation = GradientDrawable.Orientation.BR_TL
            180 -> orientation = GradientDrawable.Orientation.RIGHT_LEFT
            225 -> orientation = GradientDrawable.Orientation.TR_BL
            270 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
            315 -> orientation = GradientDrawable.Orientation.TL_BR
        }
        colors = intArrayOf(startColor, centerColor, endColor)
        cornerRadius = radius
    }
}


@BindingAdapter(value = ["binding_bg_startColor", "binding_bg_centerColor", "binding_bg_endColor", "binding_bg_angle"], requireAll = true)
fun View.bindingBgShapeGradual(@ColorInt startColor: Int, @ColorInt centerColor: Int, @ColorInt endColor: Int, angle: Int) {
    this.bindingBgShapeGradual(startColor, centerColor, endColor, angle, 0f)
}


@BindingAdapter(value = ["binding_bg_stroke", "binding_bg_strokeColor",
    "binding_bg_solidColor", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShapeStroke(stroke: Float, @ColorInt strokeColor: Int, @ColorInt solidColor: Int, radius: Float) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setStroke(stroke.toInt(), strokeColor)
        setColor(solidColor)
        cornerRadius = radius
    }
}

@BindingAdapter(value = ["binding_bg_solidColor", "binding_bg_radius"], requireAll = true)
fun View.bindingBgShape(@ColorInt solidColor: Int, radius: Float) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(solidColor)
        cornerRadius = radius
    }
}


@BindingAdapter(value = ["binding_bg_stroke", "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"], requireAll = true)
fun View.bindingBgShapeOvalStroke(stroke: Float, @ColorInt strokeColor: Int, solidOvalColor: Int) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setStroke(stroke.toInt(), strokeColor)
        setColor(solidOvalColor)
    }
}


@BindingAdapter(value = ["binding_bg_solidOvalColor"], requireAll = true)
fun View.bindingBgShapeOval(solidOvalColor: Int) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(solidOvalColor)
    }
}

@BindingAdapter(value = ["binding_bg_solidColor", "binding_bg_topLeftRadius",
    "binding_bg_topRightRadius", "binding_bg_bottomLeftRadius", "binding_bg_bottomRightRadius"], requireAll = false)
fun View.bindingBgShape(solidColor: Int, topLeftRadius: Float, topRightRadius: Float,
                        bottomLeftRadius: Float, bottomRightRadius: Float) {
    val cornerRadius = floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomLeftRadius, bottomLeftRadius,
            bottomRightRadius, bottomRightRadius)

    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(solidColor)
        cornerRadii = cornerRadius
    }
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
fun ImageView.bindingImageUrl(url: String?, drawable: Drawable) {
    ImageLoader.loadOss(this, url, drawable)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_placeholder", "binding_src_error"], requireAll = true)
fun ImageView.bindingImageUrl(url: String?, placeholder: Drawable, error: Drawable) {
    GlideApp.with(this)
            .load(url)
            .placeholder(placeholder)
            .centerInside()
            .error(error)
            .into(this)
}

@BindingAdapter(value = ["binding_src_url_circle", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingCircleImageUrl(url: String?, drawable: Drawable) {
    ImageLoader.loadCircleCropOss(this, url, drawable)
}


@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingCenterCrop(url: String, drawable: Drawable) {
    ImageLoader.loadCenterCropOss(this, url, drawable)
}

@BindingAdapter(value = ["binding_src_url", "binding_src_cornerRadius"], requireAll = true)
fun ImageView.bindingRoundedCorner(url: String, radius: Float) {
    ImageLoader.loadRoundedCornerOss(this, url, radius.toInt())
}


@BindingAdapter(value = ["binding_src_url", "binding_src_cornerRadius", "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingRoundedCorner(url: String, radius: Float, drawable: Drawable) {
    ImageLoader.loadRoundedCornerOss(this, url, radius.toInt(), drawable)
}


@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_cornerRadius"], requireAll = true)
fun ImageView.setRoundedCornerCenterCrop(url: String, radius: Float) {
    ImageLoader.loadCenterCropRoundedCornerOss(this, url, radius.toInt())
}


@BindingAdapter(value = ["binding_src_url_center_crop", "binding_src_cornerRadius",
    "binding_src_placeholder"], requireAll = true)
fun ImageView.bindingRoundedCornerCenterCrop(url: String, radius: Float, drawable: Drawable) {
    ImageLoader.loadCenterCropRoundedCornerOss(this, url, radius.toInt(), drawable)
}

@BindingAdapter(value = ["binding_src_url_fixWidth", "binding_src_baseHeight"], requireAll = true)
fun ImageView.bindingImageUrlFixWidth(url: String, baseHeight: Float) {
    GlideApp.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val v = this@bindingImageUrlFixWidth
                    v.setImageBitmap(resource)
                    val w = resource.width
                    val h = resource.height
                    if (w == 0 || h == 0) return
                    val f = w.toFloat() / h.toFloat()
                    v.layoutParams.width = (f * baseHeight).toInt()
                    v.requestLayout()
                }

            })
}

@BindingAdapter(value = ["binding_src_url_fixHeight", "binding_src_baseWidth"], requireAll = true)
fun ImageView.bindingImageUrlFixHeight(url: String, baseWidth: Float) {
    GlideApp.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val v = this@bindingImageUrlFixHeight
                    GlideApp.with(v).load(resource).into(v)
                    val w = resource.width
                    val h = resource.height
                    if (w == 0 || h == 0) return
                    val f = h.toFloat() / w.toFloat()
                    v.layoutParams.height = (f * baseWidth).toInt()
                    v.requestLayout()
                }

            })
}

@BindingAdapter("binding_src_file")
fun ImageView.bindingFileToImage(file: File?) {
    if (file != null) {
        val uri = Uri.fromFile(file)
        this.setImageURI(uri)
    }
}

@BindingAdapter("binding_src_filePath")
fun ImageView.bindingFileToImageFromPath(path: String?) {
    if (path != null) {
        val uri = Uri.parse(path)
        this.setImageURI(uri)
    }
}

@BindingAdapter("binding_img_res")
fun ImageView.bindingImageResource(res: Int) {
    this.setImageResource(res)
}

@BindingAdapter("binding_src_drawable")
fun ImageView.bindingImageDrawable(drawable: Drawable) {
    this.setImageDrawable(drawable)
}

//------------------------TextView--------------------------//

@BindingAdapter("binding_font_type")
fun TextView.bindingImpactTypeface(path: String) {
    val tf: Typeface = Typeface.createFromAsset(context.assets, path)
    this.typeface = tf
}

@BindingAdapter("binding_paint_flag")
fun TextView.bindingPaintFlag(flag: Int) {
    this.paint.flags = flag // such as Paint.STRIKE_THRU_TEXT_FLAG
    this.paint.isAntiAlias = true
}

@BindingAdapter("binding_paint_flag_is_thru")
fun TextView.bindingPaintFlagThru(flag: Boolean) {
    if (flag) {
        this.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        this.paint.isAntiAlias = true
    } else {
        this.paint.flags = 0
    }
}

@BindingAdapter("binding_textColor")
fun TextView.bindingTextColor(@ColorInt color: Int) {
    this.setTextColor(color)
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
    val header = this.refreshHeader
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
    val footer = this.refreshFooter
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
    val header = this.refreshHeader
    if (header == null) {
        this.setRefreshHeader(ClassicsHeader(this.context).apply { setPrimaryColor(color) })
    } else {
        if (header is ClassicsHeader) {
            header.setPrimaryColor(color)
        }
    }
}

@BindingAdapter("binding_srl_footerPrimaryColor")
fun SmartRefreshLayout.bindingFooterPrimaryColor(@ColorInt color: Int) {
    val footer = this.refreshFooter
    if (footer == null) {
        this.setRefreshFooter(ClassicsFooter(this.context).apply { setPrimaryColor(color) })
    } else {
        if (footer is ClassicsFooter) {
            footer.setPrimaryColor(color)
        }
    }
}

@BindingAdapter("binding_srl_headerAccentColor")
fun SmartRefreshLayout.bindingHeaderAccentColor(@ColorInt color: Int) {
    val header = this.refreshHeader
    if (header == null) {
        this.setRefreshHeader(ClassicsHeader(this.context).apply { setAccentColor(color) })
    } else {
        if (header is ClassicsHeader) {
            header.setAccentColor(color)
        }
    }
}

@BindingAdapter("binding_srl_footerAccentColor")
fun SmartRefreshLayout.bindingFooterAccentColor(@ColorInt color: Int) {
    val footer = this.refreshFooter
    if (footer == null) {
        this.setRefreshFooter(ClassicsFooter(this.context).apply { setAccentColor(color) })
    } else {
        if (footer is ClassicsFooter) {
            footer.setAccentColor(color)
        }
    }
}


