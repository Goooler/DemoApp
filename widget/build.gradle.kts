plugins {
  id(Plugins.androidLibrary)
}

setupBase().run {
  resourcePrefix = Module.Widget.resourcePrefix
  defaultConfig.versionNameSuffix = Module.Widget.versionNameSuffix
}

dependencies {
  implementation(Libs.core)
}
