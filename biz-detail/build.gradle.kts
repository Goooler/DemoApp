plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  alias(libs.plugins.moshiX)
}

android {
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidX.compose.get()
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.activity.compose)
  implementation(libs.bundles.androidX.compose)
  implementation(libs.bundles.accompanist)

  implementation(libs.square.moshi)
}
