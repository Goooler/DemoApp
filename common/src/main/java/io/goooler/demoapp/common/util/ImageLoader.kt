@file:Suppress("unused")

package io.goooler.demoapp.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import okhttp3.OkHttpClient
import java.io.InputStream

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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(bitmap)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderId)
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(bitmap)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .centerCrop()
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .centerCrop()
            .into(imageView)
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadCenterInside(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .centerInside()
            .into(imageView)
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
        errorDrawable: Drawable? = null
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .centerInside()
            .into(imageView)
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadFitCenter(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .fitCenter()
            .into(imageView)
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
        errorDrawable: Drawable? = null
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .fitCenter()
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .circleCrop()
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .circleCrop()
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param radius the rounding radius
     * @param placeholderId  the placeholder id
     */
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        @Px radius: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(imageView)
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
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        @Px radius: Int,
        placeholderDrawable: Drawable? = null,
        errorDrawable: Drawable? = null
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(imageView)
    }

    /**
     * @param imageView     the image view
     * @param url           the url
     * @param radius        blur radius
     * @param sampling      blur sampling
     * @param placeholderId the placeholder id
     */
    fun loadCenterCropBlur(
        imageView: ImageView,
        url: String?,
        @Px radius: Int,
        sampling: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView).load(url)
            .placeholder(placeholderId)
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
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
        @Px radius: Int,
        sampling: Int,
        placeholderDrawable: Drawable? = null,
        errorDrawable: Drawable? = null
    ) {
        GlideApp.with(imageView).load(url)
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
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
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    /**
     * Clear disk cache.
     *
     * @param context the context
     */
    fun clearDiskCache(context: Context) {
        GlideApp.get(context).clearDiskCache()
    }

    /**
     * Clear memory.
     *
     * @param context the context
     */
    fun clearMemory(context: Context) {
        GlideApp.get(context).clearMemory()
    }
}

@GlideModule
internal class GlideConfigModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(OkHttpClient.Builder().build())
        )
    }
}

//------------------------BindingAdapter--------------------------//

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

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_cornerRadius"
)
fun ImageView.loadCenterCropRoundedCorner(
    url: String?,
    @Px radius: Float
) {
    ImageLoader.loadCenterCropRoundedCorner(this, url, radius.toInt())
}

@BindingAdapter(
    "binding_src_url_center_crop",
    "binding_src_cornerRadius",
    "binding_src_placeholder"
)
fun ImageView.loadCenterCropRoundedCorner(
    url: String?,
    @Px radius: Float,
    placeholderDrawable: Drawable?
) {
    ImageLoader.loadCenterCropRoundedCorner(this, url, radius.toInt(), placeholderDrawable)
}