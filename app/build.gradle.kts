import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.napt)
}

android {
  val appName = "DemoApp"

  defaultConfig {
    applicationId = namespace
    targetSdk = 33
    versionCode = libs.versions.versionCode.get().toInt()
    versionName = libs.versions.versionName.get()
    resourceConfigurations += setOf("en", "zh-rCN", "xxhdpi")
  }
  buildFeatures {
    dataBinding = true
    resValues = true
  }
  lint.abortOnError = true
  val releaseSigning = signingConfigs.create("release") {
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
      signingConfig = releaseSigning
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles("$rootDir/gradle/proguard-rules.pro")
    }
    debug {
      resValue("string", "app_name", "$appName.debug")
      signingConfig = releaseSigning
      isJniDebuggable = true
      isRenderscriptDebuggable = true
      isCrunchPngs = false
    }
  }
  dependenciesInfo.includeInApk = false
  applicationVariants.configureEach {
    outputs.configureEach {
      (this as? ApkVariantOutputImpl)?.outputFileName =
        "${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}_$commitHash.apk"
    }
  }
}

dependencies {
  implementation(projects.common)

  implementation(projects.biz.login)
  implementation(projects.biz.main)
  implementation(projects.biz.detail)
  implementation(projects.biz.web)

  debugImplementation(libs.square.leakCanary)
}

fun Project.getSignProperty(key: String, path: String = "gradle/keystore.properties"): String {
  return Properties().apply { rootProject.file(path).inputStream().use(::load) }.getProperty(key)
}

val commitHash = providers.exec {
  commandLine("git", "rev-parse", "--short=7", "HEAD")
}.standardOutput.asText.get().trim()
