plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    resourcePrefix(ResourcePrefix.login)
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        versionNameSuffix = VersionNameSuffix.login
        ndk { abiFilters(ndkLibs) }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        coreLibraryDesugaringEnabled = true
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
}

dependencies {
    // local
    api(fileTree(localLibs))

    // official
    coreLibraryDesugaring(Libs.desugar)
    api(Libs.kotlin)
    api(Libs.core)
    api(Libs.appCompat)
    api(Libs.annotation)
    api(Libs.multiDex)
    api(Libs.fragment)
    api(Libs.legacySupport)
    api(Libs.preference)
    api(Libs.palette)
    api(Libs.constraintLayout)
    api(Libs.material)
    api(Libs.recyclerView)
    api(Libs.cardView)
    api(Libs.lifecycle)
    api(Libs.navigation)

    // UI
    api(Libs.smartRefreshLayout)
    api(Libs.mpChart)
    api(Libs.lottie)
    api(Libs.flycoTabLayout)
    api(Libs.baseRvHelper)

    // network
    api(Libs.tbs)
    api(Libs.okHttp)
    api(Libs.retrofit)
    api(Libs.glide)
    kapt(Libs.glideKapt)

    // storage
    api(Libs.objectBox)
    api(Libs.mmkv)

    // serializable
    api(Libs.fastjson)
    api(Libs.gson)

    // async
    api(Libs.eventBus)
    kapt(Libs.eventBusKapt)
    api(Libs.liveDataBus)
    api(Libs.rx)

    // utils
    api(Libs.luban)

    // analyze
    debugApi(Libs.leakCanary)
}