plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    defaultConfig {
        versionNameSuffix = VersionNameSuffix.common
    }
    setupFlavors()
    productFlavors.all {
        dimension("channel")
        buildConfigField("String", "VERSION_NAME", "\"$appVersionName\"")
        buildConfigField("String", "CHANNEL", "\"$name\"")
        buildConfigField("String", "CDN_PREFIX", "\"$cdnPrefix\"")
        if (name == "daily") {
            buildConfigField("String", "API_HOST", "\"${ApiHosts.daily}\"")
        } else if (name == "online") {
            buildConfigField("String", "API_HOST", "\"${ApiHosts.online}\"")
        }
    }
}

kapt {
    kaptCommon()
}

dependencies {
    api(project(Modules.base))

    api(Libs.arouter)
    kapt(Libs.arouterKapt)

    // async
    api(Libs.rx)

    // storage
    api(Libs.objectBox)
    api(Libs.mmkv)
}