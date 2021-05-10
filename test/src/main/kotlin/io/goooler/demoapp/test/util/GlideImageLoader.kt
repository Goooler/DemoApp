@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.goooler.demoapp.test.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

object GlideImageLoader {

  /**
   * load image
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun load(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {}
  }

  /**
   * Load image with centerCrop.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun loadCenterCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    loadCenterCropWithRoundedCorners(
      imageView,
      url,
      0f,
      placeholderDrawable,
      errorDrawable,
      useCache
    )
  }

  /**
   * Load image with centerInside.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun loadCenterInside(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      centerInside()
    }
  }

  /**
   * Load image with fitCenter.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun loadFitCenter(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      fitCenter()
    }
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun loadCircleCrop(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      circleCrop()
    }
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param radius      the rounding radius
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun loadWithRoundedCorners(
    imageView: ImageView,
    url: String?,
    @Px @FloatRange(from = 0.0) radius: Float,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      if (radius > 0) roundedCorners(radius)
    }
  }

  /**
   * Load image with centerCrop and roundedCorner
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param radius      the rounding radius
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error drawable
   */
  fun loadCenterCropWithRoundedCorners(
    imageView: ImageView,
    url: String?,
    @Px @FloatRange(from = 0.0) radius: Float,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      if (radius > 0) roundedCorners(radius)
      centerCrop()
    }
  }

  /**
   * @param imageView           the image view
   * @param url                 the url
   * @param radius              blur radius
   * @param sampling            blur sampling
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error drawable
   */
  fun loadCenterCropBlur(
    imageView: ImageView,
    url: String?,
    @Px @FloatRange(from = 0.0) radius: Float,
    sampling: Int,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ) {
    imageView.loadBase(url, placeholderDrawable, errorDrawable, useCache) {
      centerCrop()
      blur(radius, sampling)
    }
  }

  inline fun ImageView.loadBase(
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean,
    builder: RequestBuilder<Drawable>.() -> Unit
  ) {
    Glide.with(this)
      .load(url)
      .placeholder(placeholderDrawable)
      .error(errorDrawable)
      .loadWithCache(useCache)
      .apply(builder)
      .into(this)
  }

  fun <T : BaseRequestOptions<T>> T.loadWithCache(useCache: Boolean = true): T {
    return if (useCache) this else skipMemoryCache(false)
      .diskCacheStrategy(DiskCacheStrategy.NONE)
  }

  fun <T : BaseRequestOptions<T>> T.roundedCorners(@Px @FloatRange(from = 0.0) radius: Float): T {
    return transform(RoundedCorners(radius.toInt()))
  }

  fun <T : BaseRequestOptions<T>> T.blur(
    @Px @FloatRange(from = 0.0) radius: Float,
    sampling: Int,
  ): T {
    return transform(BlurTransformation(radius.toInt(), sampling))
  }

  /**
   * Clear disk cache.
   *
   * @param context the context
   */
  fun clearDiskCache(context: Context) {
    Glide.get(context).clearDiskCache()
  }

  /**
   * Clear memory.
   *
   * @param context the context
   */
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
  "binding_src_url_centerCrop"
)
fun ImageView.loadCenterCrop(
  url: String?
) {
  GlideImageLoader.loadCenterCrop(this, url)
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
  "binding_src_url",
  "binding_src_cornerRadius"
)
fun ImageView.loadWithRoundedCorners(
  url: String?,
  @Px radius: Float
) {
  GlideImageLoader.loadWithRoundedCorners(this, url, radius)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder"
)
fun ImageView.loadWithRoundedCorners(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?
) {
  GlideImageLoader.loadWithRoundedCorners(this, url, radius, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadWithRoundedCorners(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  GlideImageLoader.loadWithRoundedCorners(
    this,
    url,
    radius,
    placeholderDrawable,
    errorDrawable
  )
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_cornerRadius"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  url: String?,
  @Px radius: Float
) {
  GlideImageLoader.loadCenterCropWithRoundedCorners(this, url, radius)
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_cornerRadius",
  "binding_src_placeholder"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?
) {
  GlideImageLoader.loadCenterCropWithRoundedCorners(this, url, radius, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_centerCrop",
  "binding_src_cornerRadius",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  GlideImageLoader.loadCenterCropWithRoundedCorners(
    this,
    url,
    radius,
    placeholderDrawable,
    errorDrawable
  )
}
