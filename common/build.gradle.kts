import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
  id(Plugins.moshiX)
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
  api(libs.bundles.srl)
  api(libs.photoView)
  api(libs.photoView)
  api(libs.rxJava3.rxJava)
  api(libs.rxJava3.rxAndroid)
  api(libs.androidX.collection)
  api(libs.utils)

  implementation(libs.bundles.coil)
  implementation(libs.moshi)
  implementation(libs.retrofit.moshiConverter)
  implementation(libs.retrofit.rxJava3)

  implementation(libs.bundles.room)
  kapt(libs.androidX.room.compiler)

  debugImplementation(libs.chucker.debug)
  releaseImplementation(libs.chucker.release)

  testImplementation(kotlin("test-junit5"))
}
