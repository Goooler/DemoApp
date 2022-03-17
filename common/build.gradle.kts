import com.android.build.gradle.LibraryExtension

plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
  alias(libs.plugins.moshiX)
  alias(libs.plugins.androidJunit5)
}

setupCommon<LibraryExtension>(LibModule.Common) {
  buildFeatures.buildConfig = true
  productFlavors.all {
    buildConfigField(BuildConfigField.VersionCode)
    buildConfigField(BuildConfigField.VersionName)
    buildConfigField(BuildConfigField.CdnPrefix)
    buildConfigField(BuildConfigField.ApiHost)
  }
}

kapt {
  correctErrorTypes = true
  arguments {
    arg("room.incremental", "true")
  }
}

dependencies {
  api(projects.base)
  api(projects.adapter)

  api(libs.androidX.constraintLayout)
  api(libs.androidX.cardView)
  api(libs.google.material)
  api(libs.photoView)
  api(libs.bundles.srl)

  api(libs.androidX.collection)
  api(libs.utils)
  api(libs.rxJava3.java)
  api(libs.rxJava3.android)

  implementation(libs.bundles.androidX.room)
  kapt(libs.androidX.room.compiler)

  implementation(libs.bundles.coil)
  implementation(libs.square.moshi)
  implementation(libs.square.retrofit.moshi)
  implementation(libs.square.retrofit.rxJava3)

  debugImplementation(libs.chucker.debug)
  releaseImplementation(libs.chucker.release)

  testImplementation(libs.kotlin.junit5)
  androidTestImplementation(libs.kotlin.junit5)
  androidTestImplementation(libs.androidX.test.runner)
  androidTestImplementation(libs.junit5Android.core)
  androidTestRuntimeOnly(libs.junit5Android.runner)
}
