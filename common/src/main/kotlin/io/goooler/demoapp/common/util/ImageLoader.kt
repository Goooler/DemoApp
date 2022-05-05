@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.Coil
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

object ImageLoader {

  fun init(context: Context) {
    val application = context.applicationContext
    val imageLoader = coil.ImageLoader.Builder(application)
      .crossfade(true)
      .components {
        val gifDecoderFactory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
          ImageDecoderDecoder.Factory()
        else
          GifDecoder.Factory()
        add(gifDecoderFactory)
        add(SvgDecoder.Factory(false))
      }
      .build()
    Coil.setImageLoader(imageLoader)
  }

  fun load(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @Px @FloatRange(from = 0.0) cornerRadius: Float = 0F,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
      if (cornerRadius > 0) transformations(RoundedCornersTransformation(cornerRadius))
    }
  }

  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
      transformations(CircleCropTransformation())
    }
  }

  inline fun ImageView.loadBase(
    data: Any?,
    placeholderDrawable: Drawable?,
    errorDrawable: Drawable?,
    useCache: Boolean,
    builder: ImageRequest.Builder.() -> Unit = {}
  ) {
    load(data) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      loadWithCache(useCache)
      builder()
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

  fun ImageRequest.Builder.loadWithCache(useCache: Boolean): ImageRequest.Builder = apply {
    if (useCache.not()) {
      memoryCachePolicy(CachePolicy.DISABLED)
      diskCachePolicy(CachePolicy.DISABLED)
      networkCachePolicy(CachePolicy.DISABLED)
    }
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_iv_data")
internal fun ImageView.load(data: Any?) {
  ImageLoader.load(this, data)
}

@BindingAdapter(
  "binding_iv_data",
  "binding_iv_cornerRadius"
)
internal fun ImageView.load(
  data: Any?,
  @Px @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.load(this, data, cornerRadius = cornerRadius)
}

@BindingAdapter(
  "binding_iv_data",
  "binding_iv_placeholder"
)
internal fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_data",
  "binding_iv_placeholder",
  "binding_iv_error"
)
internal fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_data",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius"
)
internal fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @Px @FloatRange(from = 0.0) cornerRadius: Float
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable, cornerRadius)
}

@BindingAdapter(
  "binding_iv_data",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_cornerRadius",
  "binding_iv_useCache"
)
internal fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  @Px @FloatRange(from = 0.0) cornerRadius: Float,
  useCache: Boolean
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable, cornerRadius, useCache)
}

@BindingAdapter("binding_iv_data_circle")
internal fun ImageView.loadCircleCrop(data: Any?) {
  ImageLoader.loadCircleCrop(this, data)
}

@BindingAdapter(
  "binding_iv_data_circle",
  "binding_iv_placeholder"
)
internal fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_data_circle",
  "binding_iv_placeholder",
  "binding_iv_error"
)
internal fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_data_circle",
  "binding_iv_placeholder",
  "binding_iv_error",
  "binding_iv_useCache"
)
internal fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?,
  useCache: Boolean
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable, errorDrawable, useCache)
}
