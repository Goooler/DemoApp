plugins {
  id(Plugins.androidLibrary)
}

setupModule(Module.Web)

dependencies {
  implementation(
    Libs.browser,
    Libs.webKit
  )
}
