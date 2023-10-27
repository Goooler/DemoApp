plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.androidX.room)
  alias(libs.plugins.napt)
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

  implementation(libs.square.moshi)
  ksp(libs.square.moshi.compiler)
}
