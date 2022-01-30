import com.android.build.gradle.LibraryExtension

setupBase<LibraryExtension>(LibModule.Adapter)

dependencies {
  apis(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2,
    Libs.collection
  )
}
