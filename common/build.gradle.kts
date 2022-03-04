import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
  id(Plugins.moshiX)
}

setupCommon<LibraryExtension>(LibModule.Common) {
  buildFeatures.buildConfig = true
  productFlavors.all {
    buildConfigField(BuildConfigField.VersionCode)
    buildConfigField(BuildConfigField.VersionName)
    buildConfigField(BuildConfigField.CdnPrefix)
    buildConfigField(BuildConfigField.ApiHost)
  }
}

kapt {
  correctErrorTypes = true
  arguments {
    arg("room.incremental", "true")
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
  implementations(*Libs.coil, Libs.moshi, Libs.retrofitMoshiConverter)
  implementations(*Libs.room)
  kapt(Libs.roomCompiler)
  debugImplementations(Libs.chuckerDebug)
  releaseImplementations(Libs.chuckerRelease)

  testImplementation(kotlin("test-junit5"))
}
