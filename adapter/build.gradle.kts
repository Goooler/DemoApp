plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.napt)
  alias(libs.plugins.cacheFix)
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
