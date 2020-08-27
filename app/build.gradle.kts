import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        applicationId = appPackageName
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        ndk { abiFilters.addAll(ndkLibs) }
        addManifestPlaceholders(
            mapOf(
                "appName" to "Demo"
            )
        )
    }
    signingConfigs {
        create("sign") {
            keyAlias = "key0"
            keyPassword = "demo.app"
            storeFile = File("${rootDir.path}/gradle/demo.jks")
            storePassword = "demo.app"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["sign"]
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            resValue("string", "app_name", appName)
            proguardFiles(
                "${rootDir.path}/gradle/proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs["sign"]
            applicationIdSuffix = ".debug"
            versionNameSuffix = ".debug"
            resValue("string", "app_name", "${appName}.debug")
            isJniDebuggable = true
            isRenderscriptDebuggable = true
            isCrunchPngs = false
        }
    }
    applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "Demo_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
        }
    }
    flavorDimensions("channel")
    productFlavors {
        create("daily")
        create("online")
    }
    buildFeatures {
        dataBinding = true
    }
    lintOptions {
        isAbortOnError = false
        isCheckReleaseBuilds = false
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
    coreLibraryDesugaring(Libs.desugar)
    implementation(project(Modules.common))
    implementation(project(Modules.login))
    implementation(project(Modules.main))
    implementation(project(Modules.web))
    kapt(Libs.arouterKapt)

    debugImplementation(Libs.leakCanary)
}