plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Common)

dependencies {
    // network
    implementation(
        Libs.coil,
        Libs.okHttpLogInterceptor
    )
}
