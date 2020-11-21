plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Common)

dependencies {
    // network
    implementation(
        *Libs.glide,
        Libs.okHttpLogInterceptor
    )
}