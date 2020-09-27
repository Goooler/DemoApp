@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

@BindingAdapter("binding_src_url")
fun ImageView.bindingImageUrl(url: String?) {
    ImageLoader.load(this, url)
}

@BindingAdapter("binding_src_url_circle")
fun ImageView.bindingCircleImageUrl(url: String?) {
    ImageLoader.loadCircleCrop(this, url)
}

@BindingAdapter(
    "binding_src_url",
    "binding_src_placeholder"
)
fun ImageView.bindingImageUrl(
    url: String?,
    placeholder: Drawable?
) {
    ImageLoader.load(this, url, placeholder)
}

@BindingAdapter(
    "binding_src_url",
    "binding_src_placeholder",
    "binding_src_error"
)
fun ImageView.bindingImageUrl(
    url: String?,
    placeholder: Drawable?,
    error: Drawable?
) {
    ImageLoader.load(this, url, placeholder, error)
}

@BindingAdapter(
    "binding_src_url_circle",
    "binding_src_placeholder"
)
fun ImageView.bindingCircleImageUrl(
    url: String?,
    drawable: Drawable?
) {
    ImageLoader.loadCircleCrop(this, url, drawable)
}

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_placeholder"
)
fun ImageView.bindingCenterCrop(
    url: String?,
    drawable: Drawable?
) {
    ImageLoader.loadCenterCrop(this, url, drawable)
}

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_placeholder",
    "binding_src_error"
)
fun ImageView.bindingCenterCrop(
    url: String?,
    drawable: Drawable?,
    error: Drawable?
) {
    ImageLoader.loadCenterCrop(this, url, drawable, error)
}

@BindingAdapter(
    "binding_src_url",
    "binding_src_cornerRadius"
)
fun ImageView.bindingRoundedCorner(
    url: String?,
    radius: Float
) {
    ImageLoader.loadRoundedCorner(this, url, radius.toInt())
}

@BindingAdapter(
    "binding_src_url",
    "binding_src_cornerRadius",
    "binding_src_placeholder"
)
fun ImageView.bindingRoundedCorner(
    url: String?,
    radius: Float,
    drawable: Drawable?
) {
    ImageLoader.loadRoundedCorner(this, url, radius.toInt(), drawable)
}

@BindingAdapter(
    "binding_src_url",
    "binding_src_cornerRadius",
    "binding_src_placeholder",
    "binding_src_error"
)
fun ImageView.bindingRoundedCorner(
    url: String?,
    radius: Float,
    drawable: Drawable?,
    error: Drawable?
) {
    ImageLoader.loadRoundedCorner(this, url, radius.toInt(), drawable, error)
}

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_cornerRadius"
)
fun ImageView.setRoundedCornerCenterCrop(
    url: String?,
    radius: Float
) {
    ImageLoader.loadCenterCropRoundedCorner(this, url, radius.toInt())
}

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_cornerRadius",
    "binding_src_placeholder"
)
fun ImageView.bindingRoundedCornerCenterCrop(
    url: String?,
    radius: Float,
    drawable: Drawable?
) {
    ImageLoader.loadCenterCropRoundedCorner(this, url, radius.toInt(), drawable)
}