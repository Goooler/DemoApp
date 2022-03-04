plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
}

setupLib(LibModule.Web) {
  buildFeatures.buildConfig = true
}

dependencies {
  implementations(
    Libs.browser,
    Libs.webKit
  )
}
