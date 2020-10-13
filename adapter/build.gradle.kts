plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon(Module.Adapter, false)

dependencies {
    api(Libs.core)
    api(Libs.paging)
    api(Libs.recyclerView)
    api(Libs.viewPager2)
}