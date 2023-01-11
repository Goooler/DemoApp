plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.napt)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  implementation(projects.common)
}
