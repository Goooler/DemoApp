plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.napt)
  alias(libs.plugins.cacheFix)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  implementation(projects.common)
}
