plugins {
    id(Plugins.androidLibrary)
}

setupCore()

dependencies {
    // architecture
    api(
        *Libs.coroutines,
        *Libs.lifecycle,
        Libs.core,
        Libs.annotation
    )

    // UI
    api(
        Libs.appCompat,
        Libs.fragment,
        Libs.material,
        Libs.constraintLayout,
        Libs.cardView
    )

    // network
    api(
        Libs.okHttp,
        Libs.retrofit
    )
}