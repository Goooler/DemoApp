package io.goooler.demoapp.base.util.image.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @author Ogiso
 * Created on 4/2/2019
 */
@GlideModule
class GlideConfigModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        val builder = OkHttpClient.Builder()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(builder.build())
        )
    }
}