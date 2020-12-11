plugins {
  id(Plugins.androidLibrary)
}

setupBase().run {
  resourcePrefix = getResourcePrefix(Module.Adapter)
  defaultConfig.versionNameSuffix = getVersionNameSuffix(Module.Adapter)
}

dependencies {
  api(
    Libs.recyclerView,
    Libs.paging,
    Libs.viewPager2
  )
}
