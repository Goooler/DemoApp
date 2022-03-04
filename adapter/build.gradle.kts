import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupBase<LibraryExtension>(LibModule.Adapter)

dependencies {
  api(libs.androidX.recyclerView)
  api(libs.androidX.paging)
  api(libs.androidX.viewPager2)
  api(libs.androidX.collection)
}
