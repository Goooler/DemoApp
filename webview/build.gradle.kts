plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    resourcePrefix(ResourcePrefix.web)
    defaultConfig {
        versionNameSuffix = VersionNameSuffix.web
    }
    setupFlavors()
}

kapt {
    kaptCommon()
}

dependencies {
    implementation(project(Modules.common))
    api(Libs.tbs)
    kapt(Libs.arouterKapt)
}