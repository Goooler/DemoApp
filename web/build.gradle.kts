plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupLib(LibModule.Web) {
  buildFeatures.buildConfig = true
}

dependencies {
  implementation(libs.androidX.browser)
  implementation(libs.androidX.webKit)
}
