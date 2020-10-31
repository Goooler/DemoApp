plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCore()

dependencies {
    // architecture
    api(Libs.coroutines)
    api(Libs.core)
    api(Libs.lifecycle)
    api(Libs.annotation)

    // UI
    api(Libs.appCompat)
    api(Libs.fragment)
    api(Libs.material)
    api(Libs.constraintLayout)
    api(Libs.cardView)

    // network
    implementation(Libs.okHttp)
    implementation(Libs.retrofit)
}