import com.android.build.gradle.LibraryExtension

applyPlugins(Plugins.androidLibrary)

setupBase<LibraryExtension>(Module.Adapter)

dependencies {
  apis(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
