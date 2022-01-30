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
  apis(
    // project
    project(LibModule.Base.moduleName),
    project(LibModule.Adapter.moduleName),
    // UI
    Libs.constraintLayout,
    Libs.cardView,
    Libs.material,
    *Libs.smartRefreshLayout,
    Libs.photoView,
    // other
    *Libs.rx,
    Libs.collection,
    Libs.utils
  )
  implementations(*Libs.coil)
  debugImplementations(Libs.chuckerDebug)
  releaseImplementations(Libs.chuckerRelease)
}
