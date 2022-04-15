plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

dependencies {
  implementation(projects.common)
}
