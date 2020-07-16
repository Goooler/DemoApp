package io.goooler.demoapp.common.util

import io.goooler.demoapp.base.type.SpKeys
import io.goooler.demoapp.base.util.SpUtil
import io.goooler.demoapp.base.util.isNetworkUrl
import io.goooler.demoapp.common.model.Constants
import java.util.*

val isFirstRun: Boolean = SpUtil.getBoolean(SpKeys.SP_FIRST_RUN)

/**
 * 拼上图片前缀
 */
fun String.toLoadUrl(): String {
    return if (isNetworkUrl()) {
        this
    } else {
        Constants.IMAGE_URL_PREFIX + this
    }
}

/**
 * 获取图片宽高比例，如：/assets/img/2019/07/18/n_1563460410803_3849___size550x769.jpg
 */
fun String.getSizeByLoadUrl(defaultWidth: Int, defaultHeight: Int): List<Int> {
    val sizeList = ArrayList<Int>()
    sizeList.add(defaultWidth)
    sizeList.add(defaultHeight)
    val flag = "size"
    if (!contains(Constants.IMAGE_URL_PREFIX) || !contains(flag)) {
        return sizeList
    }
    val pattern = "$flag(\\d+x\\d+)"
    Regex(pattern)
        .findAll(this)
        .forEach {
            // size550x769
            val sizeXXXxXXX = it.value
            // 550x769
            val mXXXxXXX = sizeXXXxXXX.replace(flag, "")
            val list = mXXXxXXX.split("x")

            if (list.size < 2) {
                return sizeList
            }
            // 550
            val width = list[0].toInt()
            // 769
            val height = list[1].toInt()
            sizeList.clear()
            sizeList.add(width)
            sizeList.add(height)
            return sizeList
        }
    return sizeList
}