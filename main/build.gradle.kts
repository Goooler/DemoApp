plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
  id(Plugins.hilt)
  id(Plugins.moshiX)
}

setupLib(LibModule.Main) {
  sourceSets["main"].res.srcDirs(
    "src/main/res/core",
    "src/main/res/other"
  )
}

dependencies {
  implementations(
    Libs.coordinatorLayout,
    Libs.flycoTabLayout
  )

  implementation(Libs.hilt)
  kapt(Libs.hiltCompiler)

  implementation(Libs.moshi)
}
