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

kapt {
  correctErrorTypes = true
  arguments {
    arg("room.incremental", "true")
  }
}

dependencies {
  implementation(Libs.coordinatorLayout)
  implementation(Libs.flycoTabLayout)

  implementation(Libs.hilt)
  kapt(Libs.hiltCompiler)

  implementations(*Libs.room)
  kapt(Libs.roomCompiler)

  implementation(Libs.moshi)
}
