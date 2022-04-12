plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
  api(libs.androidX.collection)
  api(libs.androidX.paging)
  api(libs.androidX.recyclerView)
  api(libs.androidX.viewPager2)
}
