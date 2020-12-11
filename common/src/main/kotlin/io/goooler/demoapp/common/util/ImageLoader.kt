@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

object ImageLoader {
  /**
   * load image
   *
   * @param imageView     the image view
   * @param url           the url
   * @param placeholderId the placeholder id
   */
  fun load(
    imageView: ImageView,
    url: String?,
    @DrawableRes placeholderId: Int
  ) {
    imageView.load(url) {
      placeholder(placeholderId)
    }
  }

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
    errorDrawable: Drawable? = null
  ) {
    imageView.load(url) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
    }
  }

  /**
   * load image
   *
   * @param imageView     the image view
   * @param bitmap        the url
   * @param placeholderId the placeholder id
   */
  fun load(
    imageView: ImageView,
    bitmap: Bitmap?,
    @DrawableRes placeholderId: Int
  ) {
    imageView.load(bitmap) {
      placeholder(placeholderId)
    }
  }

  /**
   * load image
   *
   * @param imageView           the image view
   * @param bitmap              the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error Drawable
   */
  fun load(
    imageView: ImageView,
    bitmap: Bitmap?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null
  ) {
    imageView.load(bitmap) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
    }
  }

  /**
   * Load image with centerCrop.
   *
   * @param imageView     the image view
   * @param url           the url
   * @param placeholderId the placeholder id
   */
  fun loadCenterCrop(
    imageView: ImageView,
    url: String?,
    @DrawableRes placeholderId: Int
  ) {
    imageView.load(url) {
      placeholder(placeholderId)
      scale(Scale.FILL)
    }
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
    errorDrawable: Drawable? = null
  ) {
    imageView.load(url) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      scale(Scale.FILL)
    }
  }

  /**
   * Load image with circleCrop.
   *
   * @param imageView     the image view
   * @param url           the url
   * @param placeholderId the placeholder id
   */
  fun loadCircleCrop(
    imageView: ImageView,
    url: String?,
    @DrawableRes placeholderId: Int
  ) {
    imageView.load(url) {
      placeholder(placeholderId)
      transformations(CircleCropTransformation())
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
    errorDrawable: Drawable? = null
  ) {
    imageView.load(url) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      transformations(CircleCropTransformation())
    }
  }

  /**
   * Load image with roundedCorner.
   *
   * @param imageView      the image view
   * @param url            the url
   * @param radius the rounding radius
   * @param placeholderId  the placeholder id
   */
  fun loadRoundedCorner(
    imageView: ImageView,
    url: String?,
    @Px radius: Int,
    @DrawableRes placeholderId: Int
  ) {
    val radiusF = radius.toFloat()
    imageView.load(url) {
      placeholder(placeholderId)
      transformations(RoundedCornersTransformation(radiusF, radiusF, radiusF, radiusF))
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
  fun loadRoundedCorner(
    imageView: ImageView,
    url: String?,
    @Px radius: Int,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null
  ) {
    val radiusF = radius.toFloat()
    imageView.load(url) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      transformations(RoundedCornersTransformation(radiusF, radiusF, radiusF, radiusF))
    }
  }

  /**
   * Load image without cache.
   *
   * @param imageView     the image view
   * @param url           the url
   * @param placeholderId the placeholder id
   */
  fun loadWithoutCache(
    imageView: ImageView,
    url: String?,
    @DrawableRes placeholderId: Int
  ) {
    imageView.load(url) {
      placeholder(placeholderId)
      memoryCachePolicy(CachePolicy.DISABLED)
      diskCachePolicy(CachePolicy.DISABLED)
    }
  }

  /**
   * Load image without cache.
   *
   * @param imageView           the image view
   * @param url                 the url
   * @param placeholderDrawable the placeholder drawable
   * @param errorDrawable       the error drawable
   */
  fun loadWithoutCache(
    imageView: ImageView,
    url: String?,
    placeholderDrawable: Drawable? = null,
    errorDrawable: Drawable? = null
  ) {
    imageView.load(url) {
      placeholder(placeholderDrawable)
      error(errorDrawable)
      memoryCachePolicy(CachePolicy.DISABLED)
      diskCachePolicy(CachePolicy.DISABLED)
    }
  }
}

// ------------------------BindingAdapter--------------------------//

@BindingAdapter("binding_src_url")
fun ImageView.load(url: String?) {
  ImageLoader.load(this, url)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_placeholder"
)
fun ImageView.load(
  url: String?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.load(this, url, placeholderDrawable)
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
  ImageLoader.load(this, url, placeholderDrawable, errorDrawable)
}

@BindingAdapter("binding_src_url_circle")
fun ImageView.loadCircleCrop(url: String?) {
  ImageLoader.loadCircleCrop(this, url)
}

@BindingAdapter(
  "binding_src_url_circle",
  "binding_src_placeholder"
)
fun ImageView.loadCircleCrop(
  url: String?,
  placeholderDrawable: Drawable?,
) {
  ImageLoader.loadCircleCrop(this, url, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_center_crop",
  "binding_src_placeholder"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, url, placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url_center_crop",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadCenterCrop(
  url: String?,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadCenterCrop(this, url, placeholderDrawable, errorDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius"
)
fun ImageView.loadRoundedCorner(
  url: String?,
  @Px radius: Float
) {
  ImageLoader.loadRoundedCorner(this, url, radius.toInt())
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder"
)
fun ImageView.loadRoundedCorner(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, url, radius.toInt(), placeholderDrawable)
}

@BindingAdapter(
  "binding_src_url",
  "binding_src_cornerRadius",
  "binding_src_placeholder",
  "binding_src_error"
)
fun ImageView.loadRoundedCorner(
  url: String?,
  @Px radius: Float,
  placeholderDrawable: Drawable?,
  errorDrawable: Drawable?
) {
  ImageLoader.loadRoundedCorner(this, url, radius.toInt(), placeholderDrawable, errorDrawable)
}
