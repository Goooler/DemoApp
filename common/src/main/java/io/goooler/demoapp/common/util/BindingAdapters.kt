@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import io.goooler.demoapp.base.util.image.ImageLoader

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
    val size = url?.getSizeByLoadUrl(baseWidth.toInt(), baseHeight.toInt()) ?: ArrayList()
    layoutParams.height = baseHeight.toInt()
    layoutParams.width = if (size[0] > 0 && size[1] > 0) {
        (baseHeight / size[1] * size[0]).toInt()
    } else {
        baseWidth.toInt()
    }
    requestLayout()
    ImageLoader.loadOss(this, url)
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
    val size = url?.getSizeByLoadUrl(baseWidth.toInt(), baseHeight.toInt()) ?: ArrayList()
    layoutParams.width = baseWidth.toInt()
    layoutParams.height = if (size[0] > 0 && size[1] > 0) {
        (baseWidth / size[0] * size[1]).toInt()
    } else {
        baseHeight.toInt()
    }
    requestLayout()
    ImageLoader.loadOss(this, url)
}