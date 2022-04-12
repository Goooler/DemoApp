plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

setupLib(LibModule.Web) {
  buildFeatures.buildConfig = true
}

dependencies {
  implementation(libs.androidX.browser)
  implementation(libs.androidX.webKit)
}
