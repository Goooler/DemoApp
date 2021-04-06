@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
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
    val imageLoader = coil.ImageLoader.Builder(context.applicationContext)
      .crossfade(true)
      .componentRegistry {
        val gifDecoder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
          ImageDecoderDecoder()
        else
          GifDecoder()
        add(gifDecoder)
        add(SvgDecoder(context))
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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    loadWithCache(useCache)
  }

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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderDrawable)
    error(errorDrawable)
    loadWithCache(useCache)
  }

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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    scale(Scale.FILL)
    loadWithCache(useCache)
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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderDrawable)
    error(errorDrawable)
    scale(Scale.FILL)
    loadWithCache(useCache)
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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    transformations(CircleCropTransformation())
    loadWithCache(useCache)
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
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderDrawable)
    error(errorDrawable)
    transformations(CircleCropTransformation())
    loadWithCache(useCache)
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
  fun loadRoundedCorner(
    imageView: ImageView,
    data: Any?,
    @Px radius: Int,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadAny(data) {
    val radiusF = radius.toFloat()
    placeholder(placeholderId)
    transformations(RoundedCornersTransformation(radiusF, radiusF, radiusF, radiusF))
    loadWithCache(useCache)
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
  fun loadRoundedCorner(
    imageView: ImageView,
    data: Any?,
    @Px radius: Int,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null,
    useCache: Boolean = true
  ): Disposable = imageView.loadAny(data) {
    val radiusF = radius.toFloat()
    placeholder(placeholderDrawable)
    error(errorDrawable)
    transformations(RoundedCornersTransformation(radiusF, radiusF, radiusF, radiusF))
    loadWithCache(useCache)
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
  private fun ImageRequest.Builder.loadWithCache(useCache: Boolean): ImageRequest.Builder {
    if (useCache.not()) {
      memoryCachePolicy(CachePolicy.DISABLED)
      diskCachePolicy(CachePolicy.DISABLED)
      networkCachePolicy(CachePolicy.DISABLED)
    }
    return this
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
  placeholderDrawable: Drawable?,
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable)
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
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt())
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder"
)
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt(), placeholderDrawable)
}

@BindingAdapter(
  "binding_iv_src",
  "binding_iv_cornerRadius",
  "binding_iv_placeholder",
  "binding_iv_error"
)
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt(), placeholderDrawable, errorDrawable)
}
