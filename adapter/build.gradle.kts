applyPlugins(Plugins.androidLibrary) {
  setupBase(Module.Adapter)
}

dependencies {
  apis(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
