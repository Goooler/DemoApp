import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    defaultConfig {
        applicationId = appPackageName
        addManifestPlaceholders(manifestFields)
        ndk { abiFilters.addAll(ndkLibs) }
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
    setupFlavors()
    compileOptions.isCoreLibraryDesugaringEnabled = true
    (this as AbstractAppExtension).applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "Demo_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
        }
    }
}

kapt {
    kaptCommon()
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