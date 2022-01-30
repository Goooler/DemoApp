setupLib(LibModule.Common) {
  buildFeatures.buildConfig = true
  productFlavors.all {
    putBuildConfigIntField(BuildConfigField.VersionCode.tag, appVersionCode)
    putBuildConfigStringField(BuildConfigField.VersionName.tag, appVersionName)
    putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
    putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
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
