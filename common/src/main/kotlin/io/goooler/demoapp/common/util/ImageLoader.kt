@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.Px
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

  @JvmOverloads
  fun load(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderRes: Int = 0,
    @DrawableRes errorRes: Int = 0,
    @Px @FloatRange(from = 0.0) cornerRadius: Float = 0F,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderRes, errorRes, useCache) {
      if (cornerRadius > 0) transformations(RoundedCornersTransformation(cornerRadius))
    }
  }

  @JvmOverloads
  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderRes: Int = 0,
    @DrawableRes errorRes: Int = 0,
    useCache: Boolean = true
  ) {
    imageView.loadBase(data, placeholderRes, errorRes, useCache) {
      transformations(CircleCropTransformation())
    }
  }

  inline fun ImageView.loadBase(
    data: Any?,
    @DrawableRes placeholderRes: Int,
    @DrawableRes errorRes: Int,
    useCache: Boolean,
    builder: ImageRequest.Builder.() -> Unit = {}
  ) {
    load(data) {
      placeholder(placeholderRes)
      error(errorRes)
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
