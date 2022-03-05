import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupBase<LibraryExtension>(LibModule.Base)

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
}
