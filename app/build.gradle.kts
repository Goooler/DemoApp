plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.hilt)
}

dependencies {
  implementation(projects.common)

  implementation(projects.bizLogin)
  implementation(projects.bizMain)
  implementation(projects.bizDetail)
  implementation(projects.bizWeb)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  debugImplementation(libs.square.leakCanary)
}
