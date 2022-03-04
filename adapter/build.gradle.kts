import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupBase<LibraryExtension>(LibModule.Adapter)

dependencies {
  api(Libs.recyclerView)
  api(Libs.paging)
  api(Libs.viewPager2)
  api(Libs.collection)
}
