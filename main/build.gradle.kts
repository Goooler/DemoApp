plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
  id(libs.plugins.hilt.get().pluginId)
  id(libs.plugins.moshiX.get().pluginId)
}

setupLib(LibModule.Main) {
  sourceSets["main"].res.srcDirs(
    "src/main/res/core",
    "src/main/res/other"
  )
}

kapt {
  correctErrorTypes = true
  arguments {
    arg("room.incremental", "true")
  }
}

dependencies {
  implementation(libs.androidX.coordinatorLayout)
  implementation(libs.flycoTabLayout)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  implementation(libs.bundles.androidX.room)
  kapt(libs.androidX.room.compiler)

  implementation(libs.square.moshi)
}
