plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
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
    api(fileTree(localLibs))
    api(project(getModuleName(Module.Base)))

    // router
    api(Libs.arouter)

    // network
    implementation(Libs.glide)
    api(Libs.moshi)
    kapt(Libs.glideKapt)

    // UI
    api(Libs.smartRefreshLayout)

    // async
    api(Libs.rx)
}