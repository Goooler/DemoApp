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
    implementation(project(Modules.base))
}