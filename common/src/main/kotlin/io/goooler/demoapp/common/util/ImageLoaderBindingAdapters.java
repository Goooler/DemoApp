package io.goooler.demoapp.common.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.databinding.BindingAdapter;

public interface ImageLoaderBindingAdapters {

  @BindingAdapter("binding_iv_data")
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object
  ) {
    ImageLoader.INSTANCE.load(imageView, object);
  }

  @BindingAdapter({"binding_iv_data", "binding_iv_cornerRadius"})
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Px @FloatRange(from = 0.0) float cornerRadius
  ) {
    ImageLoader.INSTANCE.load(imageView, object, null, null, cornerRadius);
  }

  @BindingAdapter({"binding_iv_data", "binding_iv_placeholder"})
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable
  ) {
    ImageLoader.INSTANCE.load(imageView, object, placeholderDrawable);
  }

  @BindingAdapter({"binding_iv_data", "binding_iv_placeholder", "binding_iv_error"})
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable,
    @Nullable Drawable errorDrawable
  ) {
    ImageLoader.INSTANCE.load(imageView, object, placeholderDrawable, errorDrawable);
  }

  @BindingAdapter({"binding_iv_data", "binding_iv_placeholder", "binding_iv_error", "binding_iv_cornerRadius"})
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable,
    @Nullable Drawable errorDrawable,
    @Px @FloatRange(from = 0.0) float cornerRadius
  ) {
    ImageLoader.INSTANCE.load(imageView, object, placeholderDrawable, errorDrawable, cornerRadius);
  }

  @BindingAdapter({"binding_iv_data", "binding_iv_placeholder", "binding_iv_error", "binding_iv_cornerRadius", "binding_iv_useCache"})
  static void bindingLoad(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable,
    @Nullable Drawable errorDrawable,
    @Px @FloatRange(from = 0.0) float cornerRadius,
    boolean useCache
  ) {
    ImageLoader.INSTANCE.load(imageView, object, placeholderDrawable, errorDrawable, cornerRadius, useCache);
  }

  @BindingAdapter("binding_iv_data_circle")
  static void bindingLoadCircleCrop(
    @NonNull ImageView imageView,
    @Nullable Object object
  ) {
    ImageLoader.INSTANCE.loadCircleCrop(imageView, object);
  }

  @BindingAdapter({"binding_iv_data_circle", "binding_iv_placeholder"})
  static void bindingLoadCircleCrop(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable
  ) {
    ImageLoader.INSTANCE.loadCircleCrop(imageView, object, placeholderDrawable);
  }

  @BindingAdapter({"binding_iv_data_circle", "binding_iv_placeholder", "binding_iv_error"})
  static void bindingLoadCircleCrop(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable,
    @Nullable Drawable errorDrawable
  ) {
    ImageLoader.INSTANCE.loadCircleCrop(imageView, object, placeholderDrawable, errorDrawable);
  }

  @BindingAdapter({"binding_iv_data_circle", "binding_iv_placeholder", "binding_iv_error", "binding_iv_useCache"})
  static void bindingLoadCircleCrop(
    @NonNull ImageView imageView,
    @Nullable Object object,
    @Nullable Drawable placeholderDrawable,
    @Nullable Drawable errorDrawable,
    boolean useCache
  ) {
    ImageLoader.INSTANCE.loadCircleCrop(imageView, object, placeholderDrawable, errorDrawable, useCache);
  }
}
