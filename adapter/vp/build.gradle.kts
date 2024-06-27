plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.bcv)
  alias(libs.plugins.mavenPublish)
}

android {
  namespace = "io.github.goooler.adapter.vp"
  buildFeatures.dataBinding = true

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  api(libs.androidX.viewPager2)
}
