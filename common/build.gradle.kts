setupLib(Module.Common) {
  buildFeatures.buildConfig = true
  productFlavors.all {
    putBuildConfigIntField(BuildConfigField.VersionCode.tag, gitCommitDescribe)
    putBuildConfigStringField(BuildConfigField.VersionName.tag, gitCommitCount)
    putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
    putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
    putBuildConfigStringField(BuildConfigField.DoraemonKitKey.tag, ApiKey.DoraemonKit.key)
  }
}

dependencies {
  implementations(
    // network
    *Libs.coil,
    Libs.okHttpBrotliInterceptor
  )
  debugImplementations(
    Libs.leakCanary,
    Libs.doraemonKitDebug,
    Libs.chuckerDebug
  )
  releaseImplementations(
    Libs.doraemonKitRelease,
    Libs.chuckerRelease
  )
}
