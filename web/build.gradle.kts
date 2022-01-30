setupLib(LibModule.Web) {
  buildFeatures.buildConfig = true
}

dependencies {
  implementations(
    Libs.browser,
    Libs.webKit
  )
}
