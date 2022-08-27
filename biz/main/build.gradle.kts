plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.moshiX)
}

android {
  buildFeatures.dataBinding = true

  sourceSets["main"].res.srcDirs(
    "src/main/res/core",
    "src/main/res/other"
  )
}

kapt {
  correctErrorTypes = true
}

ksp {
  arg("room.incremental", "true")
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.coordinatorLayout)
  implementation(libs.flycoTabLayout)

  implementation(libs.bundles.androidX.room)
  ksp(libs.androidX.room.compiler)

  implementation(libs.square.moshi)
}
