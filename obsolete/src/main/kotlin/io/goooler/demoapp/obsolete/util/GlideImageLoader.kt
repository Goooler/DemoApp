@file:Suppress("unused", "MemberVisibilityCanBePrivate")
@file:JvmName("GlideImageLoader")

package io.goooler.demoapp.obsolete.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions

object GlideImageLoader {
  @JvmStatic
  fun load(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    if (cornerRadius > 0) transform(RoundedCorners(cornerRadius))
  }

  @JvmStatic
  fun loadCircleCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    circleCrop()
  }

  @JvmStatic
  fun loadCenterCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    if (cornerRadius > 0) transform(CenterCrop(), RoundedCorners(cornerRadius)) else centerCrop()
  }

  @JvmStatic
  inline fun ImageView.loadBase(
    url: String?,
    placeholderDrawable: Drawable?,
    errorDrawable: Drawable?,
    useCache: Boolean,
    builder: RequestBuilder<Drawable>.() -> Unit = {}
  ) {
    Glide.with(this)
      .load(url)
      .placeholder(placeholderDrawable)
      .error(errorDrawable)
      .loadWithCache(useCache)
      .apply(builder)
      .into(this)
  }

  @JvmStatic
  fun <T : BaseRequestOptions<T>> T.loadWithCache(useCache: Boolean): T {
    return if (useCache) this else skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE)
  }

  @JvmStatic
  fun clearDiskCache(context: Context) {
    Glide.get(context).clearDiskCache()
  }

  @JvmStatic
  fun clearMemory(context: Context) {
    Glide.get(context).clearMemory()
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_src_url")
fun ImageView.load(url: String?) {
  GlideImageLoader.load(this, url)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius"
)
fun ImageView.load(
  url: String?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  GlideImageLoader.load(this, url, cornerRadius = cornerRadius.toInt())
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder"
)
fun ImageView.load(
  url: String?,
  placeholderDrawable: Drawable?
) {
  GlideImageLoader.load(this, url, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.load(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  GlideImageLoader.load(this, url, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder",
  "binding_src_error",
  "binding_src_cornerRadius"
)
fun ImageView.load(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  GlideImageLoader.load(this, url, placeholderDrawable, errorDrawable, cornerRadius.toInt())
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder",
  "binding_src_error",
  "binding_src_cornerRadius",
  "binding_src_useCache"
)
fun ImageView.load(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float,
  useCache: Boolean
) {
  GlideImageLoader.load(
    this,
    url,
    placeholderDrawable,
    errorDrawable,
    cornerRadius.toInt(),
    useCache
  )
}

@BindingAdapter("binding_src_url_circle")
fun ImageView.loadCircleCrop(url: String?) {
  GlideImageLoader.loadCircleCrop(this, url)
}

@BindingAdapter(
  "binding_src_url_circle",
  "binding_src_placeholder"
)
fun ImageView.loadCircleCrop(
  url: String?,
  placeholderDrawable: Drawable?,
) {
  GlideImageLoader.loadCircleCrop(this, url, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_circle",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadCircleCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  GlideImageLoader.loadCircleCrop(this, url, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_src_url_circle",
  "binding_src_placeholder",
  "binding_src_error",
  "binding_src_useCache"
)
fun ImageView.loadCircleCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  useCache: Boolean
) {
  GlideImageLoader.loadCircleCrop(this, url, placeholderDrawable, errorDrawable, useCache)
}

@BindingAdapter(
  "binding_src_url_centerCrop"
)
fun ImageView.loadCenterCrop(
  url: String?
) {
  GlideImageLoader.loadCenterCrop(this, url)
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_cornerRadius"
)
fun ImageView.loadCenterCrop(
  url: String?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  GlideImageLoader.loadCenterCrop(
    this,
    url,
    cornerRadius = cornerRadius.toInt()
  )
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_placeholder"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?
) {
  GlideImageLoader.loadCenterCrop(this, url, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  GlideImageLoader.loadCenterCrop(this, url, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_placeholder",
  "binding_src_error",
  "binding_src_cornerRadius"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  GlideImageLoader.loadCenterCrop(
    this,
    url,
    placeholderDrawable,
    errorDrawable,
    cornerRadius.toInt()
  )
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_placeholder",
  "binding_src_error",
  "binding_src_cornerRadius",
  "binding_src_useCache"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float,
  useCache: Boolean
) {
  GlideImageLoader.loadCenterCrop(
    this,
    url,
    placeholderDrawable,
    errorDrawable,
    cornerRadius.toInt(),
    useCache
  )
}
