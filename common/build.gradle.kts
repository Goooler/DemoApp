plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon(Module.Common).run {
    productFlavors.all {
        buildConfigField("String", "VERSION_NAME", "\"$appVersionName\"")
        buildConfigField("String", "CDN_PREFIX", "\"$cdnPrefix\"")
        buildConfigField("String", "API_HOST", "\"${apiHosts[name]}\"")
    }
}

dependencies {
    api(project(getModuleName(Module.Base)))

    api(Libs.arouter)
    kapt(Libs.arouterKapt)

    // async
    api(Libs.rx)

    // storage
    api(Libs.objectBox)
    api(Libs.mmkv)
}