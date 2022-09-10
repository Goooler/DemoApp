plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.moshiX)
}

android {
  buildFeatures {
    buildConfig = true
    dataBinding = true
  }
  productFlavors.all {
    buildConfigField("Integer", "VERSION_CODE", libs.versions.versionCode.get())
    buildConfigField("String", "VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
    buildConfigField("String", "CDN_PREFIX", "\"https://raw.githubusercontent.com/\"")
    buildConfigField("String", "API_HOST", "\"https://api.github.com/\"")
  }
}

dependencies {
  api(projects.base)
  api(projects.adapter)

  api(libs.androidX.constraintLayout)
  api(libs.androidX.cardView)
  api(libs.google.material)
  api(libs.bundles.srl)

  api(libs.androidX.collection)
  api(libs.utils)

  implementation(libs.bundles.androidX.room)
  ksp(libs.androidX.room.compiler)

  implementation(libs.bundles.coil)
  implementation(libs.square.moshi)
  implementation(libs.square.retrofit.moshi)

  debugImplementation(libs.chucker.debug)
  releaseImplementation(libs.chucker.release)

  testImplementation(libs.kotlin.junit5)
  testImplementation(libs.square.okHttp.mock)
  testImplementation(libs.square.okHttp.mockJunit5)
}
