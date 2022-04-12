plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
  buildFeatures.buildConfig = true
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.browser)
  implementation(libs.androidX.webKit)
}
