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
import androidx.annotation.Px;
import androidx.databinding.BindingAdapter;

public interface BaseBindingAdapters {

  // ------------------------View --------------------------//

  @BindingAdapter("binding_isGone")
  static void bindingIsGone(View view, boolean gone) {
    view.setVisibility(gone ? View.GONE : View.VISIBLE);
  }

  @BindingAdapter("binding_isVisible")
  static void bindingIsVisible(View view, boolean show) {
    view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  @BindingAdapter("binding_isSelected")
  static void bindingIsSelect(View view, boolean select) {
    view.setSelected(select);
  }

  @BindingAdapter("binding_rect_radius")
  static void bindingRectCornerRadius(View view, Float radius) {
    view.setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(android.view.View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
      }
    });
    view.setClipToOutline(true);
  }

  @BindingAdapter({"binding_width", "binding_height"})
  static void bindingWidthAndHeight(View view, Float width, Float height) {
    view.getLayoutParams().width = width.intValue();
    view.getLayoutParams().height = height.intValue();
    view.requestLayout();
  }

  @BindingAdapter("binding_width")
  static void bindingWidth(View view, Float width) {
    view.getLayoutParams().width = width.intValue();
    view.requestLayout();
  }

  @BindingAdapter("binding_height")
  static void bindingHeight(View view, Float height) {
    view.getLayoutParams().height = height.intValue();
    view.requestLayout();
  }

  @BindingAdapter("binding_marginTop")
  static void bindingMarginTop(View view, Float margin) {
    marginDirection(view, 1, margin);
  }

  @BindingAdapter("binding_marginBottom")
  static void bindingMarginBottom(View view, Float margin) {
    marginDirection(view, 3, margin);
  }

  @BindingAdapter("binding_marginStart")
  static void bindingMarginStart(View view, Float margin) {
    marginDirection(view, 0, margin);
  }

  @BindingAdapter("binding_marginEnd")
  static void bindingMarginEnd(View view, Float margin) {
    marginDirection(view, 2, margin);
  }

// ------------------------View Bg Shape---------------------//

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle",
    "binding_bg_radius"
  })
  static void bindingBgShapeGradual(
    View view,
    @ColorInt int startColor,
    @ColorInt int endColor,
    int angle,
    @Px Float radius
  ) {
    setBgShapeGradual(
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
    View view, @ColorInt int startColor,
    @ColorInt int endColor,
    int angle,
    @Px Float stroke,
    @ColorInt int strokeColor,
    @Px Float radius
  ) {
    setBgShapeGradual(
      view,
      GradientDrawable.RECTANGLE,
      new int[]{startColor, endColor},
      angle,
      null,
      strokeColor,
      stroke,
      radius = radius
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
    View view,
    @ColorInt int startColor,
    @ColorInt int centerColor,
    @ColorInt int endColor,
    int angle,
    @Px Float radius
  ) {
    setBgShapeGradual(
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
    View view,
    @ColorInt int solidColor,
    @Px Float topLeft,
    @Px Float topRight,
    @Px Float bottomLeft,
    @Px Float bottomRight
  ) {
    setBgShapeCorners(view, solidColor, topLeft, topRight, bottomLeft, bottomRight);
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_endColor",
    "binding_bg_angle"
  })
  static void bindingBgShapeGradual(
    View view,
    @ColorInt int startColor,
    @ColorInt int endColor,
    int angle
  ) {
    setBgShapeGradual(view, GradientDrawable.RECTANGLE, new int[]{startColor, endColor}, angle, null, Color.TRANSPARENT, 0f, 0f);
  }

  @BindingAdapter({
    "binding_bg_startColor",
    "binding_bg_centerColor",
    "binding_bg_endColor",
    "binding_bg_angle"
  })
  static void bindingBgShapeGradual(
    View view,
    @ColorInt int startColor,
    @ColorInt int centerColor,
    @ColorInt int endColor,
    int angle
  ) {
    setBgShapeGradual(view, GradientDrawable.RECTANGLE, new int[]{startColor, centerColor, endColor}, angle, null, Color.TRANSPARENT, 0f, 0f);
  }

  @BindingAdapter({
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidColor",
    "binding_bg_radius"
  })
  static void bindingBgShapeStroke(View view, @Px Float stroke, @ColorInt int strokeColor, @ColorInt int solidColor, @Px Float radius) {
    setBgShapeGradual(view, GradientDrawable.RECTANGLE, null, 0, solidColor, strokeColor, stroke, radius);
  }

  @BindingAdapter({
    "binding_bg_solidColor",
    "binding_bg_radius"
  })
  static void bindingBgShape(View view, @ColorInt int solidColor, @Px Float radius) {
    setBgShapeGradual(view, GradientDrawable.RECTANGLE, null, 0, solidColor, Color.TRANSPARENT, 0f, radius);
  }

  @BindingAdapter({
    "binding_bg_stroke",
    "binding_bg_strokeColor",
    "binding_bg_solidOvalColor"
  })
  static void bindingBgShapeOvalStroke(View view, @Px Float stroke, @ColorInt int strokeColor, @ColorInt int solidOvalColor) {
    setBgShapeGradual(view, GradientDrawable.OVAL, null, 0, solidOvalColor, strokeColor, stroke, 0f);
  }

  @BindingAdapter("binding_bg_solidOvalColor")
  static void bindingBgShapeOval(View view, @ColorInt int solidOvalColor) {
    setBgShapeGradual(view, GradientDrawable.OVAL, null, 0, solidOvalColor, Color.TRANSPARENT, 0f, 0f);
  }

// ------------------------TextView--------------------------//

  @BindingAdapter("binding_font_type")
  static void bindingImpactTypeface(TextView textView, String path) {
    textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), path));
  }

  @BindingAdapter("binding_paint_flag")
  static void bindingPaintFlag(TextView textView, int flag) {
    textView.getPaint().setFlags(flag);
    textView.getPaint().setAntiAlias(true);
  }

  @BindingAdapter("binding_paint_flag_is_thru")
  static void bindingPaintFlagThru(TextView textView, boolean flag) {
    if (flag) {
      textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
      textView.getPaint().setAntiAlias(true);
    } else {
      textView.getPaint().setFlags(0);
    }
  }

// ---------------------Private-------------------------------//

  /**
   * 设置 view 的背景，支持圆形和矩形，渐变色和描边圆角等
   *
   * @param shapeType     背景形状，圆、矩形等
   * @param gradualColors 渐变色数组，和填充色互斥
   * @param angle         渐变色角度
   * @param solidColor    填充色，和渐变色数组互斥
   * @param strokeColor   描边色
   * @param stroke        描边粗细
   * @param radius        圆角大小
   */
  static void setBgShapeGradual(View view, int shapeType, @ColorInt int[] gradualColors, int angle, @ColorInt Integer solidColor, @ColorInt int strokeColor, @Px Float stroke, @Px Float radius) {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(shapeType);
    gradientDrawable.setUseLevel(false);
    gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    int remainder = angle % 45;
    int validAngle;
    if (remainder >= 22.5) {
      validAngle = angle % 360 + 45 - remainder;
    } else {
      validAngle = angle % 360 - remainder;
    }
    switch (validAngle) {
      case 45:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.BL_TR);
        break;
      case 90:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
        break;
      case 135:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.BR_TL);
        break;
      case 180:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        break;
      case 225:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TR_BL);
        break;
      case 270:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        break;
      case 315:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TL_BR);
        break;
      default:
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        break;
    }

    if (gradualColors != null && solidColor == null) {
      gradientDrawable.setColors(gradualColors);
    } else if (gradualColors == null && solidColor != null) {
      gradientDrawable.setColor(solidColor);
    }
    gradientDrawable.setStroke(stroke.intValue(), strokeColor);
    gradientDrawable.setCornerRadius(radius);
    view.setBackground(gradientDrawable);
  }

  /**
   * 设置 view 在矩形某几个角上需要圆角的背景
   *
   * @param solidColor  填充色
   * @param topLeft     左上圆角大小
   * @param topRight    右上圆角大小
   * @param bottomLeft  左下圆角大小
   * @param bottomRight 左下圆角大小
   */
  static void setBgShapeCorners(View view, @ColorInt int solidColor, @Px float topLeft, @Px float topRight, @Px float bottomLeft, @Px float bottomRight) {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(GradientDrawable.RECTANGLE);
    gradientDrawable.setColor(solidColor);
    float[] radii = {topLeft,
      topLeft,
      topRight,
      topRight,
      bottomRight,
      bottomRight,
      bottomLeft,
      bottomLeft};
    gradientDrawable.setCornerRadii(radii);
    view.setBackground(gradientDrawable);
  }

  static void marginDirection(View view, int direction, @Px Float margin) {
    if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
      ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
      switch (direction) {
        case 0:
          p.setMarginStart(margin.intValue());
          break;
        case 1:
          p.topMargin = margin.intValue();
          break;
        case 2:
          p.setMarginEnd(margin.intValue());
          break;
        default:
          p.bottomMargin = margin.intValue();
          break;
      }
      view.setLayoutParams(p);
    }
  }
}
