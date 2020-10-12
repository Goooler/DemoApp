plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon()

dependencies {
    // local
    api(fileTree(localLibs))

    // architecture
    api(Libs.coroutines)
    api(Libs.core)
    api(Libs.annotation)
    api(Libs.multiDex)
    api(Libs.lifecycle)

    // UI
    api(Libs.appCompat)
    api(Libs.fragment)
    api(Libs.material)
    api(Libs.constraintLayout)
    api(Libs.cardView)
    api(Libs.smartRefreshLayout)

    // network
    api(Libs.okHttp)
    api(Libs.retrofit)
    api(Libs.glide)
    kapt(Libs.glideKapt)

    // utils
    api(Libs.utils)

    // serializable
    api(Libs.moshi)
}