plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.napt)
}

android {
  buildFeatures.dataBinding = true
}

dependencies {
  api(libs.kotlinX.immutable)
  api(libs.androidX.collection)
  api(libs.androidX.paging)
  api(libs.androidX.recyclerView)
  api(libs.androidX.viewPager2)
}
