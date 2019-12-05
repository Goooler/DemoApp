package io.goooler.demoapp.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import io.goooler.demoapp.util.image.glide.GlideApp;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 现在服务端的下发的图片下载链接分两种；
 * 一种：http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg
 * 另外一种后缀带[!max] 和 [!small]的：http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg!max
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg!small
 * <p>
 * 现在处理方式是阿里云支持等比例压缩，规则是图片后缀加：
 * ?x-oss-process=image/resize,w_指定宽度
 * ?x-oss-process=image/resize,h_指定高度
 * 处理过程中，带[!max]和[!small]后缀的图片链接需要去掉后缀，处理为像下面这样：
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg?x-oss-process=image/resize,w_140
 * http://cdn.xxx.com/assets/file/11/24/n_1543045056389_4585.jpg?x-oss-process=image/resize,h_340
 * <p>
 * 在调用GlideApp的centerCrop()，centerInside()等方法时，如果同时也设置了ImageView的ScaleType属性，那么ImageView
 * 的ScaleType将会被忽略。
 * <p>
 * 说明：本类中方法名带有Oss后缀的表示图片URL是阿里云的CDN链接，其它链接将没有获取指定尺寸图片的效果
 *
 * @author Ogiso Created on 4/3/2019
 */
public class ImageLoader {
    private static final String OSS_MAX_SUFFIX = "!max";
    private static final String OSS_SMALL_SUFFIX = "!small";
    private static final String OSS_RESIZE_W = "?x-oss-process=image/resize,w_";
    private static final String OSS_RESIZE_H = "?x-oss-process=image/resize,h_";
    private static final String OSS_VIDEO_SNAPSHOT_W = ",w_";
    private static final String OSS_VIDEO_SNAPSHOT_H = ",h_";

    private static final String OSS_VIDEO_SNAPSHOT = "?x-oss-process=video/snapshot,t_1000,f_jpg,m_fast,ar_auto";

    private ImageLoader() {
        throw new UnsupportedOperationException("you can't instantiate me.");
    }

    /**
     * load image
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void load(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void load(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void load(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void load(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView     the image view
     * @param bitmap        the url
     * @param placeholderId the placeholder id
     */
    public static void load(ImageView imageView, Bitmap bitmap, int placeholderId) {
        GlideApp.with(imageView)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholderId)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView           the image view
     * @param bitmap              the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void load(ImageView imageView, Bitmap bitmap, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholderDrawable)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView           the image view
     * @param bitmap              the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void load(ImageView imageView, Bitmap bitmap, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * load image
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * load image
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * load image
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCenterCrop(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCenterCrop(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .centerCrop()
                .into(imageView);
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .centerCrop()
                .into(imageView);
    }


    /**
     * Load image with centerCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .centerCrop()
                .into(imageView);
    }


    /**
     * Load image with centerCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCenterCropOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .centerCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCenterCropOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .centerCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCropOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .centerCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * Load image with centerCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCenterCropOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .centerCrop()
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }


    /**
     * Load image with centerInside.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCenterInside(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .centerInside()
                .into(imageView);
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCenterInside(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .centerInside()
                .into(imageView);
    }


    /**
     * Load image with centerInside.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterInside(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .centerInside()
                .into(imageView);
    }


    /**
     * Load image with centerInside.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCenterInside(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .centerInside()
                .into(imageView);
    }


    /**
     * Load image with centerInside.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCenterInsideOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .centerInside()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCenterInsideOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .centerInside()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerInside.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterInsideOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .centerInside()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * Load image with centerInside.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCenterInsideOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .centerInside()
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadFitCenter(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .fitCenter()
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadFitCenter(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .fitCenter()
                .into(imageView);
    }


    /**
     * Load image with fitCenter.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadFitCenter(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .fitCenter()
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadFitCenter(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .fitCenter()
                .into(imageView);
    }


    /**
     * Load image with fitCenter.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadFitCenterOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .fitCenter()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadFitCenterOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .fitCenter()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadFitCenterOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .fitCenter()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with fitCenter.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadFitCenterOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .fitCenter()
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }


    /**
     * Load image with circleCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCircleCrop(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCircleCrop(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .circleCrop()
                .into(imageView);
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCircleCrop(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .circleCrop()
                .into(imageView);
    }


    /**
     * Load image with circleCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCircleCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .circleCrop()
                .into(imageView);
    }


    /**
     * Load image with circleCrop.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadCircleCropOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .circleCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadCircleCropOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .circleCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with circleCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCircleCropOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .circleCrop()
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * Load image with circleCrop.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error Drawable
     */
    public static void loadCircleCropOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .circleCrop()
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    public static void loadRoundedCorner(ImageView imageView, String url, int roundingRadius) {
        GlideApp.with(imageView)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    public static void loadRoundedCorner(ImageView imageView, String url, int roundingRadius, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadRoundedCorner(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .into(imageView);
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
    public static void loadRoundedCorner(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    public static void loadRoundedCornerOss(ImageView imageView, String url, int roundingRadius) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    public static void loadRoundedCornerOss(ImageView imageView, String url, int roundingRadius, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with roundedCorner.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadRoundedCornerOss(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
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
    public static void loadRoundedCornerOss(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    public static void loadCenterCropRoundedCorner(ImageView imageView, String url, int roundingRadius) {
        GlideApp.with(imageView)
                .load(url)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    public static void loadCenterCropRoundedCorner(ImageView imageView, String url, int roundingRadius, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCropRoundedCorner(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .into(imageView);
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
    public static void loadCenterCropRoundedCorner(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     */
    public static void loadCenterCropRoundedCornerOss(ImageView imageView, String url, int roundingRadius) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView      the image view
     * @param url            the url
     * @param roundingRadius the rounding radius
     * @param placeholderId  the placeholder id
     */
    public static void loadCenterCropRoundedCornerOss(ImageView imageView, String url, int roundingRadius, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image with centerCrop and roundedCorner
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param roundingRadius      the rounding radius
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCropRoundedCornerOss(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
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
    public static void loadCenterCropRoundedCornerOss(ImageView imageView, String url, int roundingRadius, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new RoundedCorners(roundingRadius))
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * @param imageView the image view
     * @param url       the url
     * @param radius    blur radius
     * @param sampling  blur sampling
     */
    public static void loadCenterCropBlur(ImageView imageView, String url, int radius, int sampling) {
        GlideApp.with(imageView).load(url)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * @param imageView     the image view
     * @param url           the url
     * @param radius        blur radius
     * @param sampling      blur sampling
     * @param placeholderId the placeholder id
     */
    public static void loadCenterCropBlur(ImageView imageView, String url, int radius, int sampling, int placeholderId) {
        GlideApp.with(imageView).load(url)
                .placeholder(placeholderId)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * @param imageView           the image view
     * @param url                 the url
     * @param radius              blur radius
     * @param sampling            blur sampling
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCropBlur(ImageView imageView, String url, int radius, int sampling, Drawable placeholderDrawable) {
        GlideApp.with(imageView).load(url)
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * @param imageView           the image view
     * @param url                 the url
     * @param radius              blur radius
     * @param sampling            blur sampling
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    public static void loadCenterCropBlur(ImageView imageView, String url, int radius, int sampling, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView).load(url)
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * @param imageView the image view
     * @param url       the url
     * @param radius    blur radius
     * @param sampling  blur sampling
     */
    public static void loadCenterCropBlurOss(ImageView imageView, String url, int radius, int sampling) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * @param imageView     the image view
     * @param url           the url
     * @param radius        blur radius
     * @param sampling      blur sampling
     * @param placeholderId the placeholder id
     */
    public static void loadCenterCropBlurOss(ImageView imageView, String url, int radius, int sampling, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * @param imageView           the image view
     * @param url                 the url
     * @param radius              blur radius
     * @param sampling            blur sampling
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadCenterCropBlurOss(ImageView imageView, String url, int radius, int sampling, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * @param imageView           the image view
     * @param url                 the url
     * @param radius              blur radius
     * @param sampling            blur sampling
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    public static void loadCenterCropBlurOss(ImageView imageView, String url, int radius, int sampling, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .transform(new CenterCrop(), new BlurTransformation(radius, sampling))
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Load video snapshot.
     *
     * @param imageView the image view
     * @param url       the video url
     */
    public static void loadVideoSnapshot(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url + OSS_VIDEO_SNAPSHOT)
                .into(imageView);
    }

    /**
     * Load video snapshot.
     *
     * @param imageView the image view
     * @param url       the video url
     */
    public static void loadVideoSnapshotOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url + OSS_VIDEO_SNAPSHOT))
                .into(imageView);
    }

    /**
     * Load image without cache.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadWithoutCache(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * Load image without cache.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadWithoutCache(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderId)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    /**
     * Load image without cache.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadWithoutCache(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    /**
     * Load image without cache.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    public static void loadWithoutCache(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(url)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    /**
     * Load image without cache.
     *
     * @param imageView the image view
     * @param url       the url
     */
    public static void loadWithoutCacheOss(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image without cache.
     *
     * @param imageView     the image view
     * @param url           the url
     * @param placeholderId the placeholder id
     */
    public static void loadWithoutCacheOss(ImageView imageView, String url, int placeholderId) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderId)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }

    /**
     * Load image without cache.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     */
    public static void loadWithoutCacheOss(ImageView imageView, String url, Drawable placeholderDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(GlideApp.with(imageView).load(url))
                .into(imageView);
    }


    /**
     * Load image without cache.
     *
     * @param imageView           the image view
     * @param url                 the url
     * @param placeholderDrawable the placeholder drawable
     * @param errorDrawable       the error drawable
     */
    public static void loadWithoutCacheOss(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView)
                .load(translateOssUrl(imageView, url))
                .placeholder(placeholderDrawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(GlideApp.with(imageView).load(url).error(errorDrawable))
                .into(imageView);
    }

    /**
     * Clear disk cache.
     *
     * @param context the context
     */
    public static void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    /**
     * Clear memory.
     *
     * @param context the context
     */
    public static void clearMemory(Context context) {
        GlideApp.get(context).clearMemory();
    }

    private static String translateOssUrl(ImageView imageView, String originalUrl) {
        if (originalUrl == null ||
                imageView.getLayoutParams() == null ||
                imageView.getLayoutParams().width == 0) {
            return originalUrl;
        }

        if (imageView.getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT ||
                imageView.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return originalUrl;
        }

        int resizeWidth = 0;
        int resizeHeight = 0;
        // 优先取width作为适配参数
        if (imageView.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
            ViewParent viewParent = imageView.getParent();
            if (viewParent instanceof ViewGroup) {
                ViewGroup viewGroupParent = (ViewGroup) viewParent;
                if (viewGroupParent.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT ||
                        viewGroupParent.getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    // 如果父布局width依然为MATCH_PARENT
                    if (imageView.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
                        if (viewGroupParent.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT ||
                                viewGroupParent.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                            return originalUrl;
                        } else {
                            resizeHeight = viewGroupParent.getLayoutParams().height;
                        }
                    } else {
                        resizeHeight = imageView.getLayoutParams().height;
                    }
                } else {
                    resizeWidth = viewGroupParent.getLayoutParams().width;
                }
            } else {
                if (imageView.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    return originalUrl;
                } else {
                    resizeHeight = imageView.getLayoutParams().height;
                }
            }
        } else {
            resizeWidth = imageView.getLayoutParams().width;
        }

        StringBuilder sb = new StringBuilder();
        if (originalUrl.contains(OSS_VIDEO_SNAPSHOT)) {
            sb.append(originalUrl);
            if (resizeWidth > 0) {
                sb.append(OSS_VIDEO_SNAPSHOT_W);
                sb.append(resizeWidth);
            }
            if (resizeHeight > 0) {
                sb.append(OSS_VIDEO_SNAPSHOT_H);
                sb.append(resizeHeight);
            }
        } else {
            int startIndex = originalUrl.lastIndexOf(OSS_MAX_SUFFIX) > 0 ? originalUrl.lastIndexOf(OSS_MAX_SUFFIX) : originalUrl.lastIndexOf(OSS_SMALL_SUFFIX);
            if (startIndex > 0) {
                sb.append(originalUrl.substring(0, startIndex));
            } else {
                sb.append(originalUrl);
            }

            if (resizeHeight > 0) {
                sb.append(OSS_RESIZE_H);
                sb.append(resizeHeight);
            } else {
                sb.append(OSS_RESIZE_W);
                sb.append(resizeWidth);
            }
        }

        return sb.toString();
    }
}