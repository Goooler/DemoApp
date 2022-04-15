plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
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
