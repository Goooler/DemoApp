plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCore()

dependencies {
    // local
    api(fileTree(localLibs))

    // architecture
    api(Libs.coroutines)
    api(Libs.core)
    api(Libs.lifecycle)
    api(Libs.multiDex)
    api(Libs.annotation)

    // UI
    api(Libs.appCompat)
    api(Libs.fragment)
    api(Libs.material)
    api(Libs.constraintLayout)
    api(Libs.cardView)

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