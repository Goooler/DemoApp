setupLib(LibModule.Common) {
  buildFeatures.buildConfig = true
  productFlavors.all {
    buildConfigField(BuildConfigField.VersionCode)
    buildConfigField(BuildConfigField.VersionName)
    buildConfigField(BuildConfigField.CdnPrefix)
    buildConfigField(BuildConfigField.ApiHost)
  }
}

dependencies {
  implementations(
    // network
    *Libs.coil
  )
  debugImplementations(
    Libs.leakCanary,
    Libs.chuckerDebug
  )
  releaseImplementations(
    Libs.chuckerRelease
  )
}
