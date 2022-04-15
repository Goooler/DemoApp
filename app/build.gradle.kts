import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.hilt)
}

android {
  val appName = "DemoApp"

  defaultConfig {
    applicationId = namespace
    targetSdk = 32
    versionCode = libs.versions.versionCode.get().toInt()
    versionName = libs.versions.versionName.get()
    resourceConfigurations += setOf("en", "zh-rCN", "xxhdpi")
  }
  signingConfigs.create("release") {
    keyAlias = getSignProperty("keyAlias")
    keyPassword = getSignProperty("keyPassword")
    storeFile = File(rootDir, getSignProperty("storeFile"))
    storePassword = getSignProperty("storePassword")
    enableV3Signing = true
    enableV4Signing = true
  }
  buildTypes {
    release {
      resValue("string", "app_name", appName)
      signingConfig = signingConfigs["release"]
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles("$rootDir/gradle/proguard-rules.pro")
    }
    debug {
      resValue("string", "app_name", "$appName.debug")
      signingConfig = signingConfigs["release"]
      isJniDebuggable = true
      isRenderscriptDebuggable = true
      isCrunchPngs = false
    }
  }
  dependenciesInfo.includeInApk = false
  applicationVariants.all {
    outputs.all {
      (this as? ApkVariantOutputImpl)?.outputFileName = "../../../../" +
        "${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
    }
  }
}

dependencies {
  implementation(projects.common)

  implementation(projects.biz.login)
  implementation(projects.biz.main)
  implementation(projects.biz.detail)
  implementation(projects.biz.web)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  debugImplementation(libs.square.leakCanary)
}

fun Project.getSignProperty(key: String, path: String = "gradle/keystore.properties"): String {
  return Properties().apply { rootProject.file(path).inputStream().use(::load) }.getProperty(key)
}
