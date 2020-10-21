plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCore().run {
    resourcePrefix = getResourcePrefix(Module.Adapter)
    defaultConfig.versionNameSuffix = getVersionNameSuffix(Module.Adapter)
}

dependencies {
    implementation(Libs.core)
    api(Libs.recyclerView)
    api(Libs.paging)
    api(Libs.viewPager2)
}