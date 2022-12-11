plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

android {
  buildFeatures {
    buildConfig = true
    dataBinding = true
  }
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.browser)
  implementation(libs.androidX.webKit)
}
