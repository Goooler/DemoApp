package io.goooler.demoapp.base.util.device

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.Px
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author X
 * Created by liyanfang on 2018/7/3.
 * Fix by feling on 2019/08/17.
 */
@SuppressLint("PrivateApi")
object DeviceUtil {
    /**
     * 获取屏幕宽度。
     *
     * @param context .
     * @return Screen Width
     */
    @Px
    fun getScreenWidth(context: Context): Int {
        return getPoint(context).x
    }

    /**
     * 获取屏幕高度。
     *
     * @param context .
     * @return Screen Height
     */
    @Px
    fun getScreenHeight(context: Context): Int {
        return getPoint(context).y
    }

    private fun getPoint(context: Context): Point {
        val point = Point()
        (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)
            ?.defaultDisplay?.getSize(point)
        return point
    }

    /**
     * 获取 androidID
     *
     * @param context .
     * @return .
     */
    fun getAndroidId(context: Context): String {
        return try {
            Settings.System.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ).orEmpty()
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 检测是否是中兴机器
     *
     * @return isZte
     */
    val isZte: Boolean
        get() = deviceModel.toLowerCase(Locale.getDefault()).contains("zte")

    /**
     * 获得设备型号
     *
     * @return .
     */
    val deviceModel: String = Build.MODEL.trim { it <= ' ' }

    /**
     * 是否存在导航栏
     *
     * @param context .
     * @return .
     */
    fun hasNavigationBar(context: Context): Boolean {
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        var hasNavigationBar = if (id > 0) {
            rs.getBoolean(id)
        } else false
        try {
            val systemPropertiesClass =
                Class.forName("android.os.SystemProperties")
            val m =
                systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride =
                m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }
        return hasNavigationBar
    }

    /**
     * 获取导航栏高度
     *
     * @param context .
     * @return .
     */
    fun getNavigationBarHeight(context: Context): Int {
        var vh = 0
        (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.let {
            val dm = DisplayMetrics()
            try {
                val c = Class.forName("android.view.Display")
                val method =
                    c.getMethod("getRealMetrics", DisplayMetrics::class.java)
                method.invoke(it.defaultDisplay, dm)
                vh = dm.heightPixels - it.defaultDisplay.height
            } catch (e: Exception) {
            }
        }
        return vh
    }

    /**
     * 获取操作系统版本
     *
     * @return version
     */
    val release: String = Build.VERSION.RELEASE

    /**
     * 获取设备型号
     *
     * @return device model
     */
    val model: String = Build.MODEL.trim { it <= ' ' }

    /**
     * 获取设备品牌
     *
     * @return BRAND
     */
    val brand: String = Build.BRAND

    /**
     * 获取设备制造商
     *
     * @return .
     */
    val manufacturer: String = Build.MANUFACTURER

    /**
     * 获取CPU指令集
     *
     * @return .
     */
    val cpu: String = Build.SUPPORTED_ABIS[0]

    /**
     * 获取屏幕物理尺寸（寸）
     *
     * @return .
     */
    fun getScreenSizeOfDevice(context: Context): Double {
        (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.let {
            val displayMetrics = DisplayMetrics()
            it.defaultDisplay.getRealMetrics(displayMetrics)
            val x = (displayMetrics.widthPixels / displayMetrics.xdpi.toDouble()).pow(2.0)
            val y = (displayMetrics.heightPixels / displayMetrics.ydpi.toDouble()).pow(2.0)
            return sqrt(x + y)
        }
        return 0.0
    }

    /**
     * 获取手机外部存储空间
     *
     * @return 以 M, G 为单位的容量
     */
    fun getExternalMemorySize(context: Context): String {
        val file = Environment.getExternalStorageDirectory()
        val statFs = StatFs(file.path)
        val blockSizeLong = statFs.blockSizeLong
        val blockCountLong = statFs.blockCountLong
        return Formatter.formatFileSize(
            context,
            blockCountLong * blockSizeLong
        )
    }
}