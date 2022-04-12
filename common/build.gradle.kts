plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.moshiX)
}

android {
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

  implementation(libs.bundles.androidX.room)
  kapt(libs.androidX.room.compiler)

  implementation(libs.bundles.coil)
  implementation(libs.square.moshi)
  implementation(libs.square.retrofit.moshi)

  debugImplementation(libs.chucker.debug)
  releaseImplementation(libs.chucker.release)

  testImplementation(libs.kotlin.junit5)
}

enum class BuildConfigField(val key: String, val value: Any) {
  VersionCode("VERSION_CODE", 1),
  VersionName("VERSION_NAME", "1.5"),
  CdnPrefix("CDN_PREFIX", "https://raw.githubusercontent.com/"),
  ApiHost("API_HOST", "https://api.github.com/")
}

fun com.android.build.api.dsl.VariantDimension.buildConfigField(field: BuildConfigField) {
  if (field.value is Int) {
    buildConfigField("Integer", field.key, field.value.toString())
  } else if (field.value is String) {
    buildConfigField("String", field.key, "\"${field.value}\"")
  }
}
