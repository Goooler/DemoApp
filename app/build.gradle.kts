plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    resourcePrefix(ResourcePrefix.app)
    compileSdkVersion(appTargetSdk)
    buildToolsVersion(appBuildTool)
    defaultConfig {
        applicationId = appPackageName
        minSdkVersion(appMinSdk)
        targetSdkVersion(appTargetSdk)
        versionCode = buildTime
        versionName = appVersionName
        vectorDrawables.useSupportLibrary = true
        versionNameSuffix = VersionNameSuffix.app
        ndk { abiFilters(ndkLibs) }
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
            signingConfig = signingConfigs.getByName("sign")
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            proguardFiles(
                "${rootDir.path}/gradle/proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("sign")
            isJniDebuggable = true
            isRenderscriptDebuggable = true
            isCrunchPngs = false
        }
    }
    /*applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName = "Demo_${buildType.name}_${buildTime}.apk"
        }
    }*/
    flavorDimensions("channel")
    productFlavors {
        create("daily") {}
        create("online") {}
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    lintOptions {
        isAbortOnError = false
        isCheckReleaseBuilds = false
    }
    compileOptions {
        coreLibraryDesugaringEnabled = true
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
}