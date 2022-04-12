plugins {
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
  api(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar", "*.aar"))))
  api(libs.kotlinX.coroutines)

  api(libs.androidX.core)
  api(libs.androidX.annotation)
  api(libs.androidX.appCompat)
  api(libs.androidX.activity)
  api(libs.androidX.fragment)
  api(libs.bundles.androidX.lifecycle)

  api(libs.square.okHttp)
  api(libs.square.retrofit)

  testImplementation(libs.kotlin.junit5)
}
