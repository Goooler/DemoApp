plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.napt)
  alias(libs.plugins.cacheFix)
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
