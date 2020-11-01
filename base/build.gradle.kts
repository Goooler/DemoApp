plugins {
    id(Plugins.androidLibrary)
}

setupCore()

dependencies {
    // architecture
    api(
        *Libs.coroutines,
        Libs.core,
        *Libs.lifecycle,
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
        *Libs.okHttp,
        Libs.retrofit
    )
}