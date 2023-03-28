plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.hilt)
}

kapt {
  correctErrorTypes = true
}

android {
  buildFeatures.dataBinding = true

  sourceSets["main"].res.srcDirs(
    "src/main/res/core",
    "src/main/res/other",
  )
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.coordinatorLayout)
  implementation(libs.flycoTabLayout)

  implementation(libs.bundles.androidX.room)
  ksp(libs.androidX.room.compiler)

  implementation(libs.square.moshi)
  ksp(libs.square.moshi.compiler)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
}
