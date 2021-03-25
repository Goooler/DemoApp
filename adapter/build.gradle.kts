import com.android.build.gradle.LibraryExtension

setupBase<LibraryExtension>(Module.Adapter)

dependencies {
  apis(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
