package io.goooler.demoapp.base.util;

import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.databinding.BindingAdapter;

public interface BaseBindingAdapters {

  // ------------------------View --------------------------//

  @BindingAdapter("binding_isGone")
  static void bindingIsGone(@NonNull View view, boolean gone) {
    view.setVisibility(gone ? View.GONE : View.VISIBLE);
  }

  @BindingAdapter("binding_isVisible")
  static void bindingIsVisible(@NonNull View view, boolean show) {
    view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  @BindingAdapter("binding_isSelected")
  static void bindingIsSelect(@NonNull View view, boolean select) {
    view.setSelected(select);
  }

  @BindingAdapter("binding_rect_radius")
  static void bindingRectCornerRadius(@NonNull View view, float radius) {
    view.setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(@NonNull View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
      }
    });
    view.setClipToOutline(true);
  }

  @BindingAdapter({"binding_width", "binding_height"})
  static void bindingWidthAndHeight(@NonNull View view, float width, float height) {
    view.getLayoutParams().width = (int) width;
    view.getLayoutParams().height = (int) height;
    view.requestLayout();
  }

  @BindingAdapter("binding_width")
  static void bindingWidth(@NonNull View view, float width) {
    view.getLayoutParams().width = (int) width;
    view.requestLayout();
  }

  @BindingAdapter("binding_height")
  static void bindingHeight(@NonNull View view, float height) {
    view.getLayoutParams().height = (int) height;
    view.requestLayout();
  }

  @BindingAdapter("binding_marginTop")
  static void bindingMarginTop(@NonNull View view, float margin) {
    BaseExtensionUtil.marginDirection(view, 1, margin);
  }

  @BindingAdapter("binding_marginBottom")
  static void bindingMarginBottom(@NonNull View view, float margin) {
    BaseExtensionUtil.marginDirection(view, 3, margin);
  }

  @BindingAdapter("binding_marginStart")
  static void bindingMarginStart(@NonNull View view, float margin) {
    BaseExtensionUtil.marginDirection(view, 0, margin);
  }

  @BindingAdapter("binding_marginEnd")
  static void bindingMarginEnd(@NonNull View view, float margin) {
    BaseExtensionUtil.marginDirection(view, 2, margin);
  }

// ------------------------View Bg Shape---------------------//

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_radius"
  })
  static void bindingBgShapeGradual(
    @NonNull View view,
    @ColorInt int startColor,
    @ColorInt int endColor,
    int angle,
    @Px float radius
  ) {
    BaseExtensionUtil.setBgShapeGradual(
      view,
      GradientDrawable.RECTANGLE,
      new int[]{startColor, endColor},
      angle,
      null,
      Color.TRANSPARENT,
      0f,
      radius
    );
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_radius"
  })
  static void bindingBgShapeGradual(
    @NonNull View view, @ColorInt int startColor,
    @ColorInt int endColor,
    int angle,
    @Px float stroke,
    @ColorInt int strokeColor,
    @Px float radius
  ) {
    BaseExtensionUtil.setBgShapeGradual(
      view,
      GradientDrawable.RECTANGLE,
      new int[]{startColor, endColor},
      angle,
      null,
      strokeColor,
      stroke,
      radius
    );
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_centerColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_radius"
  })
  static void bindingBgShapeGradual(
    @NonNull View view,
    @ColorInt int startColor,
    @ColorInt int centerColor,
    @ColorInt int endColor,
    int angle,
    @Px float radius
  ) {
    BaseExtensionUtil.setBgShapeGradual(
      view,
      GradientDrawable.RECTANGLE,
      new int[]{startColor, centerColor, endColor},
      angle,
      null,
      Color.TRANSPARENT,
      0f,
      radius
    );
  }

  /**
   * 这个比较特殊，requireAll = false
   */
  @BindingAdapter(
    value = {
      "binding_bg_solidColor",
      "binding_bg_topLeftRadius",
      "binding_bg_topRightRadius",
      "binding_bg_bottomLeftRadius",
      "binding_bg_bottomRightRadius"
    },
    requireAll = false
  )
  static void bindingBgShapeCorners(
    @NonNull View view,
    @ColorInt int solidColor,
    @Px float topLeft,
    @Px float topRight,
    @Px float bottomLeft,
    @Px float bottomRight
  ) {
    BaseExtensionUtil.setBgShapeCorners(view, solidColor, topLeft, topRight, bottomLeft, bottomRight);
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle"
  })
  static void bindingBgShapeGradual(
    @NonNull View view,
    @ColorInt int startColor,
    @ColorInt int endColor,
    int angle
  ) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.RECTANGLE, new int[]{startColor, endColor}, angle, null, Color.TRANSPARENT, 0f, 0f);
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_centerColor",
    "binding_bg_endColor",
    "binding_bg_angle"
  })
  static void bindingBgShapeGradual(
    @NonNull View view,
    @ColorInt int startColor,
    @ColorInt int centerColor,
    @ColorInt int endColor,
    int angle
  ) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.RECTANGLE, new int[]{startColor, centerColor, endColor}, angle, null, Color.TRANSPARENT, 0f, 0f);
  }

  @BindingAdapter({
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidColor",
    "binding_bg_radius"
  })
  static void bindingBgShapeStroke(@NonNull View view, @Px float stroke, @ColorInt int strokeColor, @ColorInt int solidColor, @Px float radius) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.RECTANGLE, null, 0, solidColor, strokeColor, stroke, radius);
  }

  @BindingAdapter({
    "binding_bg_solidColor",
    "binding_bg_radius"
  })
  static void bindingBgShape(@NonNull View view, @ColorInt int solidColor, @Px float radius) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.RECTANGLE, null, 0, solidColor, Color.TRANSPARENT, 0f, radius);
  }

  @BindingAdapter({
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"
  })
  static void bindingBgShapeOvalStroke(@NonNull View view, @Px float stroke, @ColorInt int strokeColor, @ColorInt int solidOvalColor) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.OVAL, null, 0, solidOvalColor, strokeColor, stroke, 0f);
  }

  @BindingAdapter("binding_bg_solidOvalColor")
  static void bindingBgShapeOval(@NonNull View view, @ColorInt int solidOvalColor) {
    BaseExtensionUtil.setBgShapeGradual(view, GradientDrawable.OVAL, null, 0, solidOvalColor, Color.TRANSPARENT, 0f, 0f);
  }

// ------------------------TextView--------------------------//

  @BindingAdapter("binding_font_type")
  static void bindingImpactTypeface(@NonNull TextView textView, String path) {
    textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), path));
  }

  @BindingAdapter("binding_paint_flag")
  static void bindingPaintFlag(@NonNull TextView textView, int flag) {
    textView.getPaint().setFlags(flag);
    textView.getPaint().setAntiAlias(true);
  }

  @BindingAdapter("binding_paint_flag_is_thru")
  static void bindingPaintFlagThru(@NonNull TextView textView, boolean flag) {
    if (flag) {
      textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
      textView.getPaint().setAntiAlias(true);
    } else {
      textView.getPaint().setFlags(0);
    }
  }
}
