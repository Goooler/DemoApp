@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import coil.Coil
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

object ImageLoader {

  fun init(context: Context) {
    val application = context.applicationContext
    val imageLoader = coil.ImageLoader.Builder(application)
      .crossfade(true)
      .components {
        val gifDecoder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
          ImageDecoderDecoder.Factory()
        else
          GifDecoder.Factory()
        add(gifDecoder)
        add(SvgDecoder.Factory())
      }
      .build()
    Coil.setImageLoader(imageLoader)
  }

  fun load(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderDrawable, errorDrawable, cornerRadius, useCache)
  }

  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderDrawable, errorDrawable, 0, useCache) {
      transformations(CircleCropTransformation())
    }
  }

  fun loadCenterCrop(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderDrawable, errorDrawable, cornerRadius, useCache) {
      scale(Scale.FIT)
    }
  }

  suspend fun getDrawable(
    data: Any?,
    context: Context,
    useCache: Boolean = true,
    builder: ImageRequest.Builder.() -> Unit = {}
  ): Drawable? {
    val request = ImageRequest.Builder(context)
      .data(data)
      .loadWithCache(useCache)
      .apply(builder)
      .build()
    return request.context.imageLoader.execute(request).drawable
  }

  fun ImageRequest.Builder.loadWithCache(useCache: Boolean): ImageRequest.Builder {
    if (useCache.not()) {
      memoryCachePolicy(CachePolicy.DISABLED)
      diskCachePolicy(CachePolicy.DISABLED)
      networkCachePolicy(CachePolicy.DISABLED)
    }
    return this
  }

  inline fun ImageView.loadBase(
    data: Any?,
    placeholderDrawable: Drawable?,
    errorDrawable: Drawable?,
    @IntRange(from = 0) cornerRadius: Int,
    useCache: Boolean,
    builder: ImageRequest.Builder.() -> Unit = {}
  ) {
    load(data, context.imageLoader) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      if (cornerRadius > 0) transformations(RoundedCornersTransformation(cornerRadius.toFloat()))
      loadWithCache(useCache)
      builder()
    }
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_iv_src")
fun ImageView.load(data: Any?) {
  ImageLoader.load(this, data)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_cornerRadius"
)
fun ImageView.load(
  data: Any?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.load(this, data, cornerRadius = cornerRadius.toInt())
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_placeholder"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable, cornerRadius.toInt())
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius",
  "binding_iv_useCache"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float,
  useCache: Boolean
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable, cornerRadius.toInt(), useCache)
}

@BindingAdapter("binding_iv_src_circle")
fun ImageView.loadCircleCrop(data: Any?) {
  ImageLoader.loadCircleCrop(this, data)
}

@BindingAdapter(
  "binding_iv_src_circle",
  "binding_iv_placeholder"
)
fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src_circle",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_src_circle",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_useCache"
)
fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  useCache: Boolean
) {
  ImageLoader.loadCircleCrop(
    this,
    data,
    placeholderDrawable,
    errorDrawable,
    useCache
  )
}

@BindingAdapter(
  "binding_iv_src_centerCrop"
)
fun ImageView.loadCenterCrop(
  data: Any?
) {
  ImageLoader.loadCenterCrop(this, data)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_cornerRadius"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.loadCenterCrop(this, data, cornerRadius = cornerRadius.toInt())
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_placeholder"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.loadCenterCrop(this, data, placeholderDrawable, errorDrawable, cornerRadius.toInt())
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius",
  "binding_iv_useCache"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @FloatRange(from = 0.0) cornerRadius: Float,
  useCache: Boolean
) {
  ImageLoader.loadCenterCrop(
    this,
    data,
    placeholderDrawable,
    errorDrawable,
    cornerRadius.toInt(),
    useCache
  )
}
