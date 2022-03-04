import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupBase<LibraryExtension>(LibModule.Adapter)

dependencies {
  apis(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2,
    Libs.collection
  )
}
