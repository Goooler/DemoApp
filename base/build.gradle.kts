plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  api(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar", "*.aar"))))

  api(libs.kotlinX.coroutines)
  api(libs.androidX.core)
  api(libs.androidX.annotation)
  api(libs.androidX.appCompat)
  api(libs.androidX.activity)
  api(libs.androidX.fragment)
  api(libs.bundles.androidX.lifecycle)

  api(libs.square.okHttp)
  api(libs.square.retrofit)

  testImplementation(libs.kotlin.junit5)
}
