applyPlugin(Plugins.androidLibrary)

setupBase(Module.Adapter)

dependencies {
  api(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
