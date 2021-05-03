@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.Coil
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.loadAny
import coil.request.CachePolicy
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

object ImageLoader {

  /**
   * init ImageLoader
   *
   * @param context should be the application's context
   */
  fun init(context: Context) {
    val application = context.applicationContext
    val imageLoader = coil.ImageLoader.Builder(application)
      .crossfade(true)
      .componentRegistry {
        val gifDecoder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
          ImageDecoderDecoder(application)
        else
          GifDecoder()
        add(gifDecoder)
        add(SvgDecoder(application))
      }
      .build()
    Coil.setImageLoader(imageLoader)
  }

  /**
   * load image
   *
   * @param imageView     the image view
   * @param data          the data
   * @param placeholderId the placeholder id
   * @param useCache      if use the cache
   */
  fun load(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderId, 0, useCache)

  /**
   * load image
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   * @param useCache            if use the cache
   */
  fun load(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache)

  /**
   * Load image with centerCrop.
   *
   * @param imageView     the image view
   * @param data          the data
   * @param placeholderId the placeholder id
   * @param useCache      if use the cache
   */
  fun loadCenterCrop(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderId, 0, useCache) {
    scale(Scale.FIT)
  }

  /**
   * Load image with centerCrop.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   * @param useCache            if use the cache
   */
  fun loadCenterCrop(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
    scale(Scale.FIT)
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView     the image view
   * @param data          the data
   * @param placeholderId the placeholder id
   * @param useCache      if use the cache
   */
  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderId, 0, useCache) {
    transformations(CircleCropTransformation())
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   * @param useCache            if use the cache
   */
  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
    transformations(CircleCropTransformation())
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView      the image view
   * @param data           the data
   * @param radius         the rounding radius
   * @param placeholderId  the placeholder id
   * @param useCache       if use the cache
   */
  fun loadWithRoundedCorners(
    imageView: ImageView,
    data: Any?,
    @Px @FloatRange(from = 0.0) radius: Float,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderId, 0, useCache) {
    transformations(RoundedCornersTransformation(radius, radius, radius, radius))
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param radius              the rounding radius
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   * @param useCache            if use the cache
   */
  @Suppress("MemberVisibilityCanBePrivate")
  fun loadWithRoundedCorners(
    imageView: ImageView,
    data: Any?,
    @Px @FloatRange(from = 0.0) radius: Float,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
    transformations(RoundedCornersTransformation(radius, radius, radius, radius))
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView      the image view
   * @param data           the data
   * @param radius         the rounding radius
   * @param placeholderId  the placeholder id
   * @param useCache       if use the cache
   */
  fun loadCenterCropWithRoundedCorners(
    imageView: ImageView,
    data: Any?,
    @Px @FloatRange(from = 0.0) radius: Float,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderId, 0, useCache) {
    scale(Scale.FIT)
    transformations(RoundedCornersTransformation(radius, radius, radius, radius))
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param radius              the rounding radius
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   * @param useCache            if use the cache
   */
  @Suppress("MemberVisibilityCanBePrivate")
  fun loadCenterCropWithRoundedCorners(
    imageView: ImageView,
    data: Any?,
    @Px @FloatRange(from = 0.0) radius: Float,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadBase(data, placeholderDrawable, errorDrawable, useCache) {
    scale(Scale.FIT)
    transformations(RoundedCornersTransformation(radius, radius, radius, radius))
  }

  /**
   * get drawable directly
   *
   * @param data     the data
   * @param context  the context
   * @param useCache if use the cache
   * @param builder  additional request params
   */
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
    return Coil.execute(request).drawable
  }

  /**
   * load image with or without cache
   * @param useCache if enable use cache
   */
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
    @DrawableRes placeholderId: Int,
    @DrawableRes errorId: Int,
    useCache: Boolean,
    builder: ImageRequest.Builder.() -> Unit = {}
  ): Disposable = loadAny(data) {
    placeholder(placeholderId)
    error(errorId)
    loadWithCache(useCache)
    builder()
  }

  inline fun ImageView.loadBase(
    data: Any?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean,
    builder: ImageRequest.Builder.() -> Unit = {}
  ): Disposable = loadAny(data) {
    placeholder(placeholderDrawable)
    error(errorDrawable)
    loadWithCache(useCache)
    builder()
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_iv_src")
fun ImageView.load(data: Any?) {
  ImageLoader.load(this, data)
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
  "binding_iv_src_centerCrop"
)
fun ImageView.loadCenterCrop(
  data: Any?
) {
  ImageLoader.loadCenterCrop(this, data)
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
  "binding_iv_src",
  "binding_iv_cornerRadius"
)
fun ImageView.loadWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float
) {
  ImageLoader.loadWithRoundedCorners(this, data, radius)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder"
)
fun ImageView.loadWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadWithRoundedCorners(this, data, radius, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.loadWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadWithRoundedCorners(this, data, radius, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_cornerRadius"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float
) {
  ImageLoader.loadCenterCropWithRoundedCorners(this, data, radius)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCenterCropWithRoundedCorners(this, data, radius, placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src_centerCrop",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.loadCenterCropWithRoundedCorners(
  data: Any?,
  @Px @FloatRange(from = 0.0) radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCenterCropWithRoundedCorners(
    this,
    data,
    radius,
    placeholderDrawable,
    errorDrawable
  )
}
