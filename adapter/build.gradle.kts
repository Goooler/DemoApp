plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    resourcePrefix(ResourcePrefix.adapter)
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        versionNameSuffix = VersionNameSuffix.adapter
    }
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
        freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility"
        )
    }
}

dependencies {
    implementation(project(Modules.base))
}