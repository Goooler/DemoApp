package io.goooler.demoapp.base.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.goooler.demoapp.base.util.image.glide.GlideApp
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * 现在服务端的下发的图片下载链接分两种；
 * 一种：http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg
 * 另外一种后缀带[!max] 和 [!small]的：http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg!max
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg!small
 *
 *
 * 现在处理方式是阿里云支持等比例压缩，规则是图片后缀加：
 * ?x-oss-process=image/resize,w_指定宽度
 * ?x-oss-process=image/resize,h_指定高度
 * 处理过程中，带[!max]和[!small]后缀的图片链接需要去掉后缀，处理为像下面这样：
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg?x-oss-process=image/resize,w_140
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg?x-oss-process=image/resize,h_340
 *
 *
 * 在调用GlideApp 的 centerCrop()，centerInside()等方法时，如果同时也设置了 ImageView 的 ScaleType 属性，那么 ImageView
 * 的 ScaleType 将会被忽略。
 *
 *
 * 说明：本类中方法名带有Oss后缀的表示图片URL是阿里云的CDN链接，其它链接将没有获取指定尺寸图片的效果
 *
 * @author Ogiso Created on 4/3/2019
 */
object ImageLoader {
    private const val OSS_MAX_SUFFIX = "!max"
    private const val OSS_SMALL_SUFFIX = "!small"
    private const val OSS_RESIZE_W = "?x-oss-process=image/resize,w_"
    private const val OSS_RESIZE_H = "?x-oss-process=image/resize,h_"
    private const val OSS_VIDEO_SNAPSHOT_W = ",w_"
    private const val OSS_VIDEO_SNAPSHOT_H = ",h_"
    private const val OSS_VIDEO_SNAPSHOT =
        "?x-oss-process=video/snapshot,t_1000,f_jpg,m_fast,ar_auto"

    /**
     * load image
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun load(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .into(imageView)
    }

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
     */
    fun load(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
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
     */
    fun load(
        imageView: ImageView,
        bitmap: Bitmap?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(bitmap)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(bitmap)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .into(imageView)
    }

    /**
     * load image
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * load image
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * load image
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .error(GlideApp.with(imageView).load(url))
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
    fun loadOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCenterCrop(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .centerCrop()
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
     */
    fun loadCenterCrop(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .centerCrop()
            .into(imageView)
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCenterCropOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .centerCrop()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadCenterCropOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .centerCrop()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadCenterCropOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .centerCrop()
            .error(GlideApp.with(imageView).load(url))
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
    fun loadCenterCropOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .centerCrop()
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCenterInside(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .centerInside()
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
     */
    fun loadCenterInside(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .centerInside()
            .into(imageView)
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCenterInsideOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .centerInside()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadCenterInsideOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .centerInside()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadCenterInsideOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .centerInside()
            .error(GlideApp.with(imageView).load(url))
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
    fun loadCenterInsideOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .centerInside()
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadFitCenter(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .fitCenter()
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
     */
    fun loadFitCenter(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .fitCenter()
            .into(imageView)
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadFitCenterOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .fitCenter()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadFitCenterOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .fitCenter()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadFitCenterOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .fitCenter()
            .error(GlideApp.with(imageView).load(url))
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
    fun loadFitCenterOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .fitCenter()
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCircleCrop(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .circleCrop()
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
     */
    fun loadCircleCrop(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .circleCrop()
            .into(imageView)
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadCircleCropOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .circleCrop()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadCircleCropOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .circleCrop()
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadCircleCropOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .circleCrop()
            .error(GlideApp.with(imageView).load(url))
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
    fun loadCircleCropOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .circleCrop()
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    fun loadRoundedCorner(
        imageView: ImageView,
        url: String?, roundingRadius: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    fun loadRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    fun loadRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    fun loadRoundedCornerOss(
        imageView: ImageView,
        url: String?, roundingRadius: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    fun loadRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    fun loadRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderId)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    fun loadCenterCropRoundedCorner(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    fun loadCenterCropRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    fun loadCenterCropRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadCenterCropRoundedCornerOss(
        imageView: ImageView,
        url: String?, roundingRadius: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    fun loadCenterCropRoundedCornerOss(
        imageView: ImageView,
        url: String?,
        roundingRadius: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), RoundedCorners(roundingRadius))
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * @param imageView the image view
     * @param url       the url
     * @param radius    blur radius
     * @param sampling  blur sampling
     */
    fun loadCenterCropBlur(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int
    ) {
        GlideApp.with(imageView).load(url)
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url))
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
        radius: Int,
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
     */
    fun loadCenterCropBlur(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView).load(url)
            .placeholder(placeholderDrawable)
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
        radius: Int,
        sampling: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView).load(url)
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * @param imageView the image view
     * @param url       the url
     * @param radius    blur radius
     * @param sampling  blur sampling
     */
    fun loadCenterCropBlurOss(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * @param imageView     the image view
     * @param url           the url
     * @param radius        blur radius
     * @param sampling      blur sampling
     * @param placeholderId the placeholder id
     */
    fun loadCenterCropBlurOss(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
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
     */
    fun loadCenterCropBlurOss(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
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
    fun loadCenterCropBlurOss(
        imageView: ImageView,
        url: String?,
        radius: Int,
        sampling: Int,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .transform(CenterCrop(), BlurTransformation(radius, sampling))
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
            .into(imageView)
    }

    /**
     * Load video snapshot.
     *
     * @param imageView the image view
     * @param url       the video url
     */
    fun loadVideoSnapshot(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url + OSS_VIDEO_SNAPSHOT)
            .into(imageView)
    }

    /**
     * Load video snapshot.
     *
     * @param imageView the image view
     * @param url       the video url
     */
    fun loadVideoSnapshotOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(
                translateOssUrl(
                    imageView,
                    url + OSS_VIDEO_SNAPSHOT
                )
            )
            .into(imageView)
    }

    /**
     * Load image without cache.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadWithoutCache(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
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
     */
    fun loadWithoutCache(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(url)
            .placeholder(placeholderDrawable)
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
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
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
     * Load image without cache.
     *
     * @param imageView the image view
     * @param url       the url
     */
    fun loadWithoutCacheOss(
        imageView: ImageView,
        url: String?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image without cache.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    fun loadWithoutCacheOss(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderId: Int
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderId)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(GlideApp.with(imageView).load(url))
            .into(imageView)
    }

    /**
     * Load image without cache.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    fun loadWithoutCacheOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(GlideApp.with(imageView).load(url))
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
    fun loadWithoutCacheOss(
        imageView: ImageView,
        url: String?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?
    ) {
        GlideApp.with(imageView)
            .load(translateOssUrl(imageView, url))
            .placeholder(placeholderDrawable)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(GlideApp.with(imageView).load(url).error(errorDrawable))
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

    private fun translateOssUrl(
        imageView: ImageView,
        originalUrl: String?
    ): String? {
        if (originalUrl == null || imageView.layoutParams == null || imageView.layoutParams.width == 0
        ) {
            return originalUrl
        }
        if (imageView.layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT ||
            imageView.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT
        ) {
            return originalUrl
        }
        var resizeWidth = 0
        var resizeHeight = 0
        // 优先取width作为适配参数
        if (imageView.layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            val viewParent = imageView.parent
            if (viewParent is ViewGroup) {
                val viewGroupParent = viewParent
                if (viewGroupParent.layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT ||
                    viewGroupParent.layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT
                ) {
                    // 如果父布局width依然为MATCH_PARENT
                    resizeHeight =
                        if (imageView.layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                            if (viewGroupParent.layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT ||
                                viewGroupParent.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT
                            ) {
                                return originalUrl
                            } else {
                                viewGroupParent.layoutParams.height
                            }
                        } else {
                            imageView.layoutParams.height
                        }
                } else {
                    resizeWidth = viewGroupParent.layoutParams.width
                }
            } else {
                resizeHeight =
                    if (imageView.layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                        return originalUrl
                    } else {
                        imageView.layoutParams.height
                    }
            }
        } else {
            resizeWidth = imageView.layoutParams.width
        }
        val sb = StringBuilder()
        if (originalUrl.contains(OSS_VIDEO_SNAPSHOT)) {
            sb.append(originalUrl)
            if (resizeWidth > 0) {
                sb.append(OSS_VIDEO_SNAPSHOT_W)
                sb.append(resizeWidth)
            }
            if (resizeHeight > 0) {
                sb.append(OSS_VIDEO_SNAPSHOT_H)
                sb.append(resizeHeight)
            }
        } else {
            val startIndex =
                if (originalUrl.lastIndexOf(OSS_MAX_SUFFIX) > 0) originalUrl.lastIndexOf(
                    OSS_MAX_SUFFIX
                ) else originalUrl.lastIndexOf(OSS_SMALL_SUFFIX)
            if (startIndex > 0) {
                sb.append(originalUrl.substring(0, startIndex))
            } else {
                sb.append(originalUrl)
            }
            if (resizeHeight > 0) {
                sb.append(OSS_RESIZE_H)
                sb.append(resizeHeight)
            } else {
                sb.append(OSS_RESIZE_W)
                sb.append(resizeWidth)
            }
        }
        return sb.toString()
    }
}