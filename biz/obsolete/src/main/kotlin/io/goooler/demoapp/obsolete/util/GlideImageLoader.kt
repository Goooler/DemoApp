@file:Suppress("unused", "MemberVisibilityCanBePrivate", "LongParameterList")

package io.goooler.demoapp.obsolete.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.IntRange
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions

object GlideImageLoader {

  fun load(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true,
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    if (cornerRadius > 0) transform(RoundedCorners(cornerRadius))
  }

  fun loadCircleCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true,
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    circleCrop()
  }

  fun loadCenterCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    @IntRange(from = 0) cornerRadius: Int = 0,
    useCache: Boolean = true,
  ) = imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
    if (cornerRadius > 0) transform(CenterCrop(), RoundedCorners(cornerRadius)) else centerCrop()
  }

  inline fun ImageView.loadBase(
    url: String?,
    placeholderDrawable: Drawable?,
    errorDrawable: Drawable?,
    useCache: Boolean,
    builder: RequestBuilder<Drawable>.() -> Unit = {},
  ) {
    Glide.with(this)
      .load(url)
      .placeholder(placeholderDrawable)
      .error(errorDrawable)
      .loadWithCache(useCache)
      .apply(builder)
      .into(this)
  }

  fun <T : BaseRequestOptions<T>> T.loadWithCache(useCache: Boolean): T {
    return if (useCache) this else skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE)
  }

  fun clearDiskCache(context: Context) {
    Glide.get(context).clearDiskCache()
  }

  fun clearMemory(context: Context) {
    Glide.get(context).clearMemory()
  }
}
