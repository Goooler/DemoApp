plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.napt)
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
