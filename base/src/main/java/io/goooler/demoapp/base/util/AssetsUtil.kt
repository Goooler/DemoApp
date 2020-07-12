package io.goooler.demoapp.base.util

import android.content.res.AssetManager
import io.goooler.demoapp.base.core.BaseApplication
import java.io.*

/**
 * 读取 asset 目录下文件的工具
 */

object AssetsUtil {
    private val assetsManager: AssetManager = BaseApplication.context.assets

    fun readStringFromAssets(fileName: String): String? {
        var jsonString: String? = null
        try {
            val builder = StringBuilder()
            val reader = BufferedReader(
                InputStreamReader(
                    assetsManager.open(fileName)
                )
            )
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

    fun getFileFromAssets(fileName: String, savePath: String): File? {
        return createFileFromInputStream(assetsManager.open(fileName), savePath)
    }

    private fun createFileFromInputStream(inputStream: InputStream, path: String): File? {
        try {
            val file = File(path)
            val outputStream: OutputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            inputStream.close()
            return file
        } catch (e: IOException) {
            //Logging exception
        }
        return null
    }
}
