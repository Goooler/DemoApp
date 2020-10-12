plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    resourcePrefix(ResourcePrefix.adapter)
    defaultConfig {
        versionNameSuffix = VersionNameSuffix.adapter
    }
}

dependencies {
    api(Libs.core)
    api(Libs.paging)
    api(Libs.recyclerView)
    api(Libs.viewPager2)
}