setupModule(Module.Common) {
  productFlavors.all {
    putBuildConfigIntField(BuildConfigField.VersionCode.tag, globalVersionCode)
    putBuildConfigStringField(BuildConfigField.VersionName.tag, globalVersionName)
    putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
    putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
    putBuildConfigStringField(BuildConfigField.DoraemonKitKey.tag, ApiKey.DoraemonKit.key)
  }
}

dependencies {
  implementations(
    // network
    Libs.coil,
    Libs.okHttpLogInterceptor,
    Libs.okHttpBrotliInterceptor
  )
  debugImplementations(
    Libs.leakCanary,
    Libs.doraemonKitDebug
  )
  releaseImplementations(Libs.doraemonKitRelease)
}
