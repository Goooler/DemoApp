@file:Suppress("unused")

package io.goooler.demoapp.base.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

object BitmapUtil {

    /**
     * BitmapFactory.decodeResource(resource, id) 在 5.0 以上会出现空指针
     */
    fun getBitmap(context: Context, @DrawableRes id: Int): Bitmap? {
        var bitmap: Bitmap? = null
        ContextCompat.getDrawable(context, id)?.let {
            bitmap = Bitmap.createBitmap(
                it.intrinsicWidth,
                it.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            if (bitmap != null) {
                val canvas = Canvas(bitmap!!)
                it.setBounds(0, 0, canvas.width, canvas.height)
                it.draw(canvas)
            }
        }
        return bitmap
    }
}