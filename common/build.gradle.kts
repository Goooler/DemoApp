plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        versionNameSuffix = VersionNameSuffix.common
    }
    flavorDimensions("channel")
    productFlavors {
        create("daily")
        create("online")
    }
    productFlavors.all {
        setDimension("channel")
        buildConfigField("String", "CHANNEL", "\"$name\"")
        buildConfigField("String", "CDN_PREFIX", "\"$cdnPrefix\"")
        if (name == "daily") {
            buildConfigField("String", "API_HOST", "\"${ApiHosts.daily}\"")
        } else if (name == "online") {
            buildConfigField("String", "API_HOST", "\"${ApiHosts.online}\"")
        }
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
        arg("objectbox.debug", true)
    }
}

dependencies {
    api(project(Modules.base))

    api(Libs.arouter)
    kapt(Libs.arouterKapt)

    // network
    api(Libs.coil)

    // storage
    api(Libs.objectBox)
    api(Libs.mmkv)
}