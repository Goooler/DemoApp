plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  implementation(projects.common)
}
