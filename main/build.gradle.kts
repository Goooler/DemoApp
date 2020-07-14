plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    resourcePrefix(ResourcePrefix.main)
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        versionNameSuffix = VersionNameSuffix.main
    }
    flavorDimensions("channel")
    productFlavors {
        create("daily") {}
        create("online") {}
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
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
    implementation(project(Modules.adapter))
    kapt(Libs.arouterKapt)
    kapt(Libs.objectBoxKapt)
}