plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    resourcePrefix(ResourcePrefix.login)
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        versionNameSuffix = VersionNameSuffix.login
    }
    flavorDimensions("channel")
    productFlavors {
        create("daily")
        create("online")
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
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

dependencies {
    implementation(project(Modules.common))
    kapt(Libs.arouterKapt)
}