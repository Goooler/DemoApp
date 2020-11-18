plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Common)

dependencies {
    api(
        // local
        fileTree(localLibs),
        project(getModuleName(Module.Base)),

        // router
        Libs.arouter,

        // UI
        *Libs.smartRefreshLayout,
        Libs.photoView,

        // utils
        *Libs.room,
        *Libs.rx,
        Libs.utils,
        Libs.permissionX,
        *Libs.moshi
    )

    // network
    implementation(
        *Libs.glide,
        Libs.okHttpLogInterceptor
    )
}