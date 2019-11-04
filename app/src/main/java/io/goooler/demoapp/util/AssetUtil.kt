package io.goooler.demoapp.util

import android.content.res.AssetManager
import io.goooler.demoapp.base.BaseApplication
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * 读取 asset 目录下文件的工具
 */

object AssetUtil {
    private val assetsManager: AssetManager
        get() = BaseApplication.context.assets

    fun readJsonFromAssets(fileName: String): String? {
        var jsonString: String? = null
        try {
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(
                    assetsManager.open(fileName)))
            while ({ jsonString = reader.readLine(); jsonString }() != null) {
                builder.append(jsonString)
            }
            jsonString = builder.toString()
            reader.close()
        } catch (e: IOException) {
            // do nothing
        }

        return jsonString
    }
}
