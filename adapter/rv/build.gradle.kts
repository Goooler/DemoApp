plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.bcv)
  alias(libs.plugins.napt)
  alias(libs.plugins.mavenPublish)
}

android {
  namespace = "io.github.goooler.adapter.rv"
  buildFeatures.dataBinding = true
}

kotlin {
  explicitApi()
}

dependencies {
  api(libs.androidX.collection)
  api(libs.androidX.paging)
  api(libs.androidX.recyclerView)
}
