import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon().run {
    defaultConfig {
        applicationId = appPackageName
        addManifestPlaceholders(manifestFields)
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
    compileOptions.isCoreLibraryDesugaringEnabled = true
    (this as AbstractAppExtension).applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "Demo_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
        }
    }
}

dependencies {
    coreLibraryDesugaring(Libs.desugar)
    implementation(project(getModuleName(Module.Common)))
    implementation(project(getModuleName(Module.Login)))
    implementation(project(getModuleName(Module.Main)))
    implementation(project(getModuleName(Module.Web)))
    kapt(Libs.arouterKapt)

    debugImplementation(Libs.leakCanary)
}