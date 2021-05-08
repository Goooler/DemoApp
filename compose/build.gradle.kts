setupLib(Module.Compose) {
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = composeVersion
}

dependencies {
  implementations(*Libs.compose, *Libs.accompanist)
}
