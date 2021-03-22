@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.annotation.SuppressLint
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

@SuppressLint("StaticFieldLeak")
object ImageLoader {

  private lateinit var mContext: Context

  fun init(context: Context) {
    mContext = context.applicationContext
    val imageLoader = coil.ImageLoader.Builder(mContext)
      .crossfade(true)
      .componentRegistry {
        val gifDecoder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoderDecoder()
        else GifDecoder()
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
   */
  fun load(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    if (useCache.not()) withoutCache()
  }

  /**
   * load image
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
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
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with centerCrop.
   *
   * @param imageView     the image view
   * @param data          the data
   * @param placeholderId the placeholder id
   */
  fun loadCenterCrop(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    scale(Scale.FILL)
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with centerCrop.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
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
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView     the image view
   * @param data          the data
   * @param placeholderId the placeholder id
   */
  fun loadCircleCrop(
    imageView: ImageView,
    data: Any?,
    @DrawableRes placeholderId: Int,
    useCache: Boolean = true
  ): Disposable = imageView.loadAny(data) {
    placeholder(placeholderId)
    transformations(CircleCropTransformation())
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
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
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView      the image view
   * @param data           the data
   * @param radius         the rounding radius
   * @param placeholderId  the placeholder id
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
    if (useCache.not()) withoutCache()
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView           the image view
   * @param data                the data
   * @param radius              the rounding radius
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
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
    if (useCache.not()) withoutCache()
  }

  suspend fun getDrawable(
    uri: String?,
    context: Context? = null,
    useCache: Boolean = true,
    builder: ImageRequest.Builder.() -> Unit = {}
  ): Drawable? {
    val request = ImageRequest.Builder(context ?: mContext)
      .data(uri)
      .apply {
        if (useCache.not()) withoutCache()
        builder()
      }
      .build()
    return Coil.execute(request).drawable
  }

  private fun ImageRequest.Builder.withoutCache(): ImageRequest.Builder {
    memoryCachePolicy(CachePolicy.DISABLED)
    diskCachePolicy(CachePolicy.DISABLED)
    return this
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_src_url")
fun ImageView.load(data: Any?) {
  ImageLoader.load(this, data)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.load(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.load(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter("binding_src_url_circle")
fun ImageView.loadCircleCrop(data: Any?) {
  ImageLoader.loadCircleCrop(this, data)
}

@BindingAdapter(
  "binding_src_url_circle",
  "binding_src_placeholder"
)
fun ImageView.loadCircleCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
) {
  ImageLoader.loadCircleCrop(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_center_crop",
  "binding_src_placeholder"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, data, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_center_crop",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadCenterCrop(
  data: Any?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, data, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius"
)
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt())
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder"
)
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt(), placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadRoundedCorner(
  data: Any?,
  @Px radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, data, radius.toInt(), placeholderDrawable, errorDrawable)
}
