plugins {
  id(Plugins.androidLibrary)
}

setupBase().run {
  resourcePrefix = Module.Adapter.resourcePrefix
  defaultConfig.versionNameSuffix = Module.Adapter.versionNameSuffix
}

dependencies {
  api(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
