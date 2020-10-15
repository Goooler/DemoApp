@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.util.image.ImageLoader

//------------------------ImageLoader--------------------------//

@BindingAdapter(
    "binding_src_url_fixWidth",
    "binding_src_baseWidth",
    "binding_src_baseHeight"
)
fun ImageView.bindingImageUrlFixWidth(
    url: String?,
    baseWidth: Float,
    baseHeight: Float
) {
    url?.let {
        val size = it.getPicSizeByUrl(baseWidth.toInt(), baseHeight.toInt())
        layoutParams.height = baseHeight.toInt()
        layoutParams.width = if (size[0] > 0 && size[1] > 0) {
            (baseHeight / size[1] * size[0]).toInt()
        } else {
            baseWidth.toInt()
        }
        requestLayout()
        ImageLoader.load(this, it)
    }
}

@BindingAdapter(
    "binding_src_url_fixHeight",
    "binding_src_baseWidth",
    "binding_src_baseHeight"
)
fun ImageView.bindingImageUrlFixHeight(
    url: String?,
    baseWidth: Float,
    baseHeight: Float
) {
    url?.let {
        val size = it.getPicSizeByUrl(baseWidth.toInt(), baseHeight.toInt())
        layoutParams.width = baseWidth.toInt()
        layoutParams.height = if (size[0] > 0 && size[1] > 0) {
            (baseWidth / size[0] * size[1]).toInt()
        } else {
            baseHeight.toInt()
        }
        requestLayout()
        ImageLoader.load(this, it)
    }
}

//------------------------SmartRefreshLayout--------------------------//

@BindingAdapter("binding_srl_refreshFinish")
fun SmartRefreshLayout.bindingRefreshFinish(isFinish: Boolean) {
    if (isFinish) finishRefresh()
}

@BindingAdapter("binding_srl_loadMoreFinish")
fun SmartRefreshLayout.bindingLoadMoreFinish(isFinish: Boolean) {
    if (isFinish) finishLoadMore()
}

@BindingAdapter("binding_srl_enableLoadMore")
fun SmartRefreshLayout.bindingEnableLoadMore(enable: Boolean) {
    setEnableLoadMore(enable)
}

@BindingAdapter("binding_srl_enableRefresh")
fun SmartRefreshLayout.bindingEnableRefresh(enable: Boolean) {
    setEnableRefresh(enable)
}

@BindingAdapter("binding_srl_noMore")
fun SmartRefreshLayout.bindingNoMoreData(haveNoMore: Boolean) {
    setNoMoreData(haveNoMore)
}

@BindingAdapter("binding_srl_headerEmpty")
fun SmartRefreshLayout.bindingHeaderEmpty(isEmpty: Boolean) {
    (refreshHeader as? ClassicsHeader)?.forEach {
        it.alpha = if (isEmpty) 0f else 1f
    }
}

@BindingAdapter("binding_srl_footerEmpty")
fun SmartRefreshLayout.bindingFooterEmpty(isEmpty: Boolean) {
    (refreshFooter as? ClassicsFooter)?.forEach {
        it.alpha = if (isEmpty) 0f else 1f
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