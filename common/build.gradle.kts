import com.android.build.gradle.LibraryExtension

setupCommon<LibraryExtension>(LibModule.Common) {
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
    projects.base,
    projects.adapter,
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
