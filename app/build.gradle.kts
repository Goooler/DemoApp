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
      resValue("string", "app_name", "${appName}.debug")
      signingConfig = signingConfigs["release"]
      isJniDebuggable = true
      isRenderscriptDebuggable = true
      isCrunchPngs = false
    }
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
    // https://youtrack.jetbrains.com/issue/KT-41985
    freeCompilerArgs += listOf(
      "-progressive",
      "-opt-in=kotlin.RequiresOptIn",
      "-Xjvm-default=all"
    )
  }
  dependenciesInfo.includeInApk = false
  applicationVariants.all {
    outputs.all {
      (this as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)?.outputFileName =
        "../../../../" +
          "${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
    }
  }
}

dependencies {
  implementation(projects.common)

  implementation(projects.bizLogin)
  implementation(projects.bizMain)
  implementation(projects.bizDetail)
  implementation(projects.bizWeb)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  debugImplementation(libs.square.leakCanary)
}

fun Project.getSignProperty(
  key: String, path: String = "gradle/keystore.properties"
): String = Properties().apply {
  rootProject.file(path).inputStream().use(::load)
}.getProperty(key)
