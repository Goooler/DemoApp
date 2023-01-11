plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.napt)
  alias(libs.plugins.cacheFix)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  implementation(projects.common)
}
