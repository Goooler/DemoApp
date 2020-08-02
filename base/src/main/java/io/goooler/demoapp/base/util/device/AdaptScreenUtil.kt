@file:Suppress("unused")

package io.goooler.demoapp.base.util.device

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import java.lang.reflect.Field
import java.util.*

/**
 * @author niechaoqun
 * pt 适配的工具类
 */
object AdaptScreenUtil {
    private var metricsFields: MutableList<Field>? = null

    /**
     * 适配屏幕宽度
     *
     * @param resources   .
     * @param designWidth 设计稿的屏幕宽度，单位 pt
     * @return 适配后的 resources
     */
    fun adaptWidth(
        resources: Resources,
        designWidth: Int
    ): Resources {
        val newXdpi =
            resources.displayMetrics.widthPixels * 72f / designWidth
        applyDisplayMetrics(resources, newXdpi)
        return resources
    }

    /**
     * 取消适配
     *
     * @param resources .
     * @return 取消适配后的 resources
     */
    fun closeAdapt(resources: Resources): Resources {
        val newXdpi =
            Resources.getSystem().displayMetrics.density * 72f
        applyDisplayMetrics(resources, newXdpi)
        return resources
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private fun applyDisplayMetrics(
        resources: Resources,
        newXdpi: Float
    ) {
        resources.displayMetrics.xdpi = newXdpi
        applyOtherDisplayMetrics(resources, newXdpi)
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private fun applyOtherDisplayMetrics(
        resources: Resources,
        newXdpi: Float
    ) {
        if (metricsFields == null) {
            metricsFields = ArrayList()
            var resCls: Class<*> = resources.javaClass
            var declaredFields = resCls.declaredFields
            while (declaredFields.isNotEmpty()) {
                declaredFields.forEach { field ->
                    if (field.type.isAssignableFrom(DisplayMetrics::class.java)) {
                        field.isAccessible = true
                        getMetricsFromField(resources, field)?.let {
                            metricsFields!!.add(field)
                            it.xdpi = newXdpi
                        }
                    }
                }
                resCls = resCls.superclass as Class<*>
                declaredFields = resCls.declaredFields
            }
        } else {
            applyMetricsFields(resources, newXdpi)
        }
    }

    /**
     * @param resources .
     * @param newXdpi   .
     */
    private fun applyMetricsFields(
        resources: Resources,
        newXdpi: Float
    ) {
        metricsFields?.forEach {
            try {
                (it[resources] as? DisplayMetrics)?.xdpi = newXdpi
            } catch (e: Exception) {
                Log.e("AdaptScreenUtil", "applyMetricsFields: $e")
            }
        }
    }

    /**
     * @param resources .
     * @param field     .
     * @return .
     */
    private fun getMetricsFromField(
        resources: Resources,
        field: Field
    ): DisplayMetrics? {
        return try {
            field[resources] as DisplayMetrics
        } catch (e: Exception) {
            Log.e("AdaptScreenUtil", "getMetricsFromField: $e")
            null
        }
    }
}