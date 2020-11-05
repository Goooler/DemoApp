plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Common).run {
    productFlavors.all {
        putBuildConfigIntField(BuildConfigField.VersionCode.tag, appVersionCode)
        putBuildConfigStringField(BuildConfigField.VersionName.tag, appVersionName)
        putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
        putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
    }
}

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