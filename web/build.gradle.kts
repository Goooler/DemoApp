setupLib(Module.Web) {
  buildFeatures.buildConfig = true
}

dependencies {
  implementations(
    Libs.browser,
    Libs.webKit
  )
}
