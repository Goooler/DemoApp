package io.goooler.demoapp.util.device;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author X
 * Created by liyanfang on 2018/7/3.
 * Fix by feling on 2019/08/17.
 */
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
    public static int getScreenWidth(@NonNull Context context) {
        Point point = new Point();
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(point);
        return point.x;
    }

    /**
     * 获取屏幕高度。
     *
     * @param context .
     * @return Screen Height
     */
    public static int getScreenHeight(@NonNull Context context) {
        Point point = new Point();
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(point);
        return point.y;
    }


    /**
     * 获取androidID
     *
     * @param context 上下文
     * @return android ID
     */
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
    public static String getDeviceModel() {
        return Build.MODEL.trim();
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context .
     * @return NavigationBar is exist.
     */
    @SuppressLint("PrivateApi")
    public static boolean checkDeviceHaveNavigationBar(@NonNull Context context) {
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
        }
        return hasNavigationBar;
    }

    /**
     * 获取虚拟功能NavigationBar键高度
     *
     * @param context .
     * @return NavigationBar键高度.
     */
    public static int getVirtualBarHeight(@NonNull Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
        }

        return vh;
    }


    /**
     * 获取操作系统版本
     *
     * @return version
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备型号
     *
     * @return device model
     */
    public static String getModel() {
        return Build.MODEL.trim();
    }

    /**
     * 获取设备品牌
     *
     * @return BRAND
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备制造商
     *
     * @return MANUFACTURER
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取CPU指令集
     *
     * @return SUPPORTED_ABIS
     */
    public static String getCpu() {
        return Build.SUPPORTED_ABIS[0];
    }

    /**
     * 获取屏幕物理尺寸（寸）
     *
     * @return double
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
     * 获取MAC地址
     *
     * @return mac
     */
    public static String getMac(@NonNull Context context) {
        String mac = getWifiMac();
        if (TextUtils.isEmpty(mac) || !mac.contains(":")) {
            mac = getEthernetMac();
            if (TextUtils.isEmpty(mac)) {
                mac = getMacAddress();
            }
        }
        if (TextUtils.isEmpty(mac)) {
            mac = getAndroidId(context);
        }
        return TextUtils.isEmpty(mac) ? "" : mac.toLowerCase();
    }

    /**
     * 获取内存大小
     *
     * @return ram
     */
    public static String getRam() {
        String path = "/proc/meminfo";
        String result = null;

        int totalRam = 0;

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr, 8192);
            result = br.readLine().split("\\s+")[1];
            br.close();
        } catch (IOException e) {
        }

        if (result != null) {
            totalRam = (int) Math.ceil((Float.valueOf(Float.valueOf(result) / (1024 * 1024)).doubleValue()));
        }

        return totalRam + "GB";
    }

    /**
     * 获取Wi-Fi Mac
     *
     * @return mac
     */
    public static String getWifiMac() {
        String macSerial = null;
        LineNumberReader lnr = null;
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            lnr = new LineNumberReader(isr);

            String str;
            if ((str = lnr.readLine()) != null) {
                macSerial = str.trim();
            }
        } catch (IOException e) {
        } finally {
            try {
                if (lnr != null) {
                    lnr.close();
                }
            } catch (IOException e) {
            }
        }
        return macSerial;
    }


    /**
     * 获取Ethernet Mac
     *
     * @return mac
     */
    public static String getEthernetMac() {
        BufferedReader reader = null;
        String ethernetMac = null;
        try {
            reader = new BufferedReader(new FileReader("sys/class/net/eth0/address"));
            ethernetMac = reader.readLine();
            if (ethernetMac != null) {
                ethernetMac = ethernetMac.trim();
            }
        } catch (Exception e) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        return ethernetMac;
    }

    /**
     * 获取 Mac地址
     *
     * @return MacAddress
     */
    public static String getMacAddress() /* throws UnknownHostException */ {
        String strMacAddr = null;
        try {
            InetAddress ip = getLocalNetAddress();

            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }

                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString();
        } catch (Exception e) {
        }

        return strMacAddr;
    }

    public static InetAddress getLocalNetAddress() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> enNetInterface = NetworkInterface.getNetworkInterfaces();
            while (enNetInterface.hasMoreElements()) {
                NetworkInterface ni = enNetInterface.nextElement();
                Enumeration<InetAddress> enIp = ni.getInetAddresses();
                while (enIp.hasMoreElements()) {
                    ip = enIp.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        break;
                    } else {
                        ip = null;
                    }
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
        }
        return ip;
    }

    /**
     * 获取手机外部存储空间
     *
     * @return 以M, G为单位的容量
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getExternalMemorySize(@NonNull Context context) {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        return Formatter.formatFileSize(context, blockCountLong * blockSizeLong);
    }

    /**
     * 获取设备指纹
     *
     * @param context .
     * @return hardwareInfo + androidId
     */
    public static String getDeviceFingerprint(@NonNull Context context) {
        String hardwareInfo = Build.ID + Build.DISPLAY + Build.PRODUCT
                + Build.DEVICE + Build.BOARD + Build.MANUFACTURER
                + Build.BRAND + Build.MODEL + Build.BOOTLOADER
                + Build.HARDWARE + Build.TYPE + Build.TAGS
                + Build.FINGERPRINT + Build.HOST + Build.USER /* + Build.SERIAL */;

        String androidId = Settings.System.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return hardwareInfo + androidId;
    }
}
