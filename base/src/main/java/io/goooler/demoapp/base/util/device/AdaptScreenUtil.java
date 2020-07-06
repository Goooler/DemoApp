package io.goooler.demoapp.base.util.device;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niechaoqun
 * pt 适配的工具类
 */
@SuppressWarnings("rawtypes")
public final class AdaptScreenUtil {

    private static List<Field> metricsFields;

    private AdaptScreenUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 适配屏幕宽度
     *
     * @param resources   .
     * @param designWidth 设计稿的屏幕宽度，单位 pt
     * @return 适配后的 resources
     */
    @NonNull
    public static Resources adaptWidth(@NonNull final Resources resources, final int designWidth) {
        float newXdpi = (resources.getDisplayMetrics().widthPixels * 72f) / designWidth;
        applyDisplayMetrics(resources, newXdpi);
        return resources;
    }

    /**
     * 取消适配
     *
     * @param resources .
     * @return 取消适配后的 resources
     */
    @NonNull
    public static Resources closeAdapt(@NonNull final Resources resources) {
        float newXdpi = Resources.getSystem().getDisplayMetrics().density * 72f;
        applyDisplayMetrics(resources, newXdpi);
        return resources;
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private static void applyDisplayMetrics(@NonNull final Resources resources, final float newXdpi) {
        resources.getDisplayMetrics().xdpi = newXdpi;
        applyOtherDisplayMetrics(resources, newXdpi);
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private static void applyOtherDisplayMetrics(@NonNull final Resources resources, final float newXdpi) {
        if (metricsFields == null) {
            metricsFields = new ArrayList<>();
            Class resCls = resources.getClass();
            Field[] declaredFields = resCls.getDeclaredFields();
            while (declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
                        field.setAccessible(true);
                        DisplayMetrics tmpDm = getMetricsFromField(resources, field);
                        if (tmpDm != null) {
                            metricsFields.add(field);
                            tmpDm.xdpi = newXdpi;
                        }
                    }
                }
                resCls = resCls.getSuperclass();
                if (resCls != null) {
                    declaredFields = resCls.getDeclaredFields();
                } else {
                    break;
                }
            }
        } else {
            applyMetricsFields(resources, newXdpi);
        }
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private static void applyMetricsFields(@NonNull final Resources resources, final float newXdpi) {
        for (Field metricsField : metricsFields) {
            try {
                DisplayMetrics dm = (DisplayMetrics) metricsField.get(resources);
                if (dm != null) {
                    dm.xdpi = newXdpi;
                }
            } catch (Exception e) {
                Log.e("AdaptScreenUtil", "applyMetricsFields: " + e);
            }
        }
    }

    /**
     * @param resources .
     * @param field     .
     * @return .
     */
    @Nullable
    private static DisplayMetrics getMetricsFromField(@NonNull final Resources resources, final Field field) {
        try {
            return (DisplayMetrics) field.get(resources);
        } catch (Exception e) {
            Log.e("AdaptScreenUtil", "getMetricsFromField: " + e);
            return null;
        }
    }
}