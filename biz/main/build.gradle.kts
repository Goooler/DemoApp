plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
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
  ksp(libs.androidX.room.compiler)

  implementation(libs.square.moshi)
  ksp(libs.square.moshi.compiler)

  implementation("com.github.limbowangqi:FloatWindow:1.0.1")
}
