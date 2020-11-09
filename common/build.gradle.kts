plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Common)

dependencies {
    // local
    api(
        fileTree(localLibs),
        project(getModuleName(Module.Base))
    )

    // router
    api(Libs.arouter)

    // network
    implementation(
        *Libs.glide,
        Libs.okHttpLogInterceptor
    )
    kapt(Libs.glideKapt)
    api(*Libs.moshi)

    // UI
    api(
        *Libs.smartRefreshLayout,
        Libs.photoView
    )

    // utils
    api(
        *Libs.rx,
        Libs.utils,
        Libs.permissionX
    )
}