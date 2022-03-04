plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
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
