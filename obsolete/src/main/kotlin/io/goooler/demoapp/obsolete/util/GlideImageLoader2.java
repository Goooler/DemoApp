package io.goooler.demoapp.obsolete.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.FloatRange;

class GlideImageLoader2 {

  public static void load(ImageView imageView, String url) {
    GlideImageLoader.load(imageView, url, null, null, 0, true);
  }

  public static void load(ImageView imageView, String url, @FloatRange(from = 0.0) Float cornerRadius) {
    GlideImageLoader.load(imageView, url, null, null, cornerRadius.intValue(), true);
  }

  public static void load(ImageView imageView, String url, Drawable placeholderDrawable) {
    GlideImageLoader.load(imageView, url, placeholderDrawable, null, 0, true);
  }

  public static void load(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
    GlideImageLoader.load(imageView, url, placeholderDrawable, errorDrawable, 0, true);
  }

  public static void load(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable, @FloatRange(from = 0.0) Float cornerRadius) {
    GlideImageLoader.load(imageView, url, placeholderDrawable, errorDrawable, cornerRadius.intValue(), true);
  }

  public static void load(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable, @FloatRange(from = 0.0) Float cornerRadius, Boolean useCache) {
    GlideImageLoader.load(imageView, url, placeholderDrawable, errorDrawable, cornerRadius.intValue(), useCache);
  }

  public static void loadCircleCrop(ImageView imageView, String url) {
    GlideImageLoader.loadCircleCrop(imageView, url, null, null, true);
  }

  public static void loadCircleCrop(ImageView imageView, String url, Drawable placeholderDrawable) {
    GlideImageLoader.loadCircleCrop(imageView, url, placeholderDrawable, null, true);
  }

  public static void loadCircleCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
    GlideImageLoader.loadCircleCrop(imageView, url, placeholderDrawable, errorDrawable, true);
  }

  public static void loadCircleCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable, Boolean useCache) {
    GlideImageLoader.loadCircleCrop(imageView, url, placeholderDrawable, errorDrawable, useCache);
  }

  public static void loadCenterCrop(ImageView imageView, String url) {
    GlideImageLoader.loadCenterCrop(imageView, url, null, null, 0, true);
  }

  public static void loadCenterCrop(ImageView imageView, String url, @FloatRange(from = 0.0) Float cornerRadius) {
    GlideImageLoader.loadCenterCrop(imageView, url, null, null, cornerRadius.intValue(), true);
  }

  public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable) {
    GlideImageLoader.loadCenterCrop(imageView, url, placeholderDrawable, null, 0, true);
  }

  public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
    GlideImageLoader.loadCenterCrop(imageView, url, placeholderDrawable, errorDrawable, 0, true);
  }

  public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable, @FloatRange(from = 0.0) Float cornerRadius) {
    GlideImageLoader.loadCenterCrop(imageView, url, placeholderDrawable, errorDrawable, cornerRadius.intValue(), true);
  }

  public static void loadCenterCrop(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable, @FloatRange(from = 0.0) Float cornerRadius, Boolean useCache) {
    GlideImageLoader.loadCenterCrop(imageView, url, placeholderDrawable, errorDrawable, cornerRadius.intValue(), useCache);
  }
}
