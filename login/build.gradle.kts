plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    resourcePrefix(ResourcePrefix.login)
    defaultConfig {
        versionNameSuffix = VersionNameSuffix.login
    }
    setupFlavors()
}

kapt {
    kaptCommon()
}

dependencies {
    implementation(project(Modules.common))
    kapt(Libs.arouterKapt)
}