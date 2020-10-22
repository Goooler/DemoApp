import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupApp().run {
    defaultConfig {
        applicationId = appPackageName
        addManifestPlaceholders(manifestFields)
    }
    buildTypes {
        getByName("release") {
            resValue("string", "app_name", appName)
        }
        getByName("debug") {
            resValue("string", "app_name", "${appName}.debug")
        }
    }
    (this as AbstractAppExtension).applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "Demo_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
        }
    }
}

dependencies {
    implementation(project(getModuleName(Module.Common)))
    implementation(project(getModuleName(Module.Login)))
    implementation(project(getModuleName(Module.Main)))
    implementation(project(getModuleName(Module.Web)))

    debugImplementation(Libs.leakCanary)
}