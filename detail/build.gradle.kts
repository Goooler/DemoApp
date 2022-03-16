plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
}

setupLib(LibModule.Detail) {
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidX.compose.get()
}

dependencies {
  implementation(libs.androidX.activity.compose)
  implementation(libs.bundles.androidX.compose)
}
