package io.goooler.demoapp.base.util.device;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

import java.io.File;
import java.lang.reflect.Method;

import io.goooler.demoapp.base.util.LogUtil;

/**
 * @author X
 * Created by liyanfang on 2018/7/3.
 * Fix by feling on 2019/08/17.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@SuppressLint("PrivateApi")
public class DeviceUtil {

    private DeviceUtil() {
        throw new UnsupportedOperationException("you can't instantiate me.");
    }

    /**
     * 获取屏幕宽度。
     *
     * @param context .
     * @return Screen Width
     */
    @Px
    public static int getScreenWidth(@NonNull Context context) {
        return getPoint(context).x;
    }

    /**
     * 获取屏幕高度。
     *
     * @param context .
     * @return Screen Height
     */
    @Px
    public static int getScreenHeight(@NonNull Context context) {
        return getPoint(context).y;
    }

    @NonNull
    private static Point getPoint(@NonNull Context context) {
        Point point = new Point();
        WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        if (wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        return point;
    }

    /**
     * 获取 androidID
     *
     * @param context .
     * @return .
     */
    @NonNull
    public static String getAndroidId(@NonNull Context context) {
        try {
            ContentResolver cr = context.getContentResolver();
            String androidId = Settings.System.getString(cr, Settings.Secure.ANDROID_ID);
            return androidId == null ? "" : androidId;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 检测是否是中兴机器
     *
     * @return isZte
     */
    public static boolean isZte() {
        return getDeviceModel().toLowerCase().contains("zte");
    }

    /**
     * 获得设备型号
     *
     * @return .
     */
    @NonNull
    public static String getDeviceModel() {
        return Build.MODEL.trim();
    }

    /**
     * 是否存在导航栏
     *
     * @param context .
     * @return .
     */
    public static boolean hasNavigationBar(@NonNull Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            LogUtil.d(e);
        }
        return hasNavigationBar;
    }

    /**
     * 获取导航栏高度
     *
     * @param context .
     * @return .
     */
    public static int getNavigationBarHeight(@NonNull Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            LogUtil.d(e);
        }
        return vh;
    }


    /**
     * 获取操作系统版本
     *
     * @return version
     */
    @NonNull
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备型号
     *
     * @return device model
     */
    @NonNull
    public static String getModel() {
        return Build.MODEL.trim();
    }

    /**
     * 获取设备品牌
     *
     * @return BRAND
     */
    @NonNull
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备制造商
     *
     * @return .
     */
    @NonNull
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取CPU指令集
     *
     * @return .
     */
    @NonNull
    public static String getCpu() {
        return Build.SUPPORTED_ABIS[0];
    }

    /**
     * 获取屏幕物理尺寸（寸）
     *
     * @return .
     */
    public static double getScreenSizeOfDevice(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getRealMetrics(displayMetrics);
            double x = Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2);
            double y = Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2);

            return Math.sqrt(x + y);
        }

        return 0;
    }

    /**
     * 获取手机外部存储空间
     *
     * @return 以 M, G 为单位的容量
     */
    @NonNull
    public static String getExternalMemorySize(@NonNull Context context) {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        return Formatter.formatFileSize(context, blockCountLong * blockSizeLong);
    }
}
