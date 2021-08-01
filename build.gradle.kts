buildscript {
  apply(extraScriptPath)

  repositories {
    google()
    gradlePluginPortal()
  }
  classpaths(
    rootProject.extra["androidPlugin"].toString(),
    rootProject.extra["kotlinPlugin"].toString(),
    Libs.hiltPlugin,
    Libs.arouterPlugin,
    Libs.kotlinterPlugin
  )
}

allprojects {
  apply("$rootDir/$extraScriptPath")
  applyPlugins(
    Plugins.kotlinter,
    Plugins.picCompress,
    Plugins.customTrans
  )
}

tasks {
  create<Delete>("clean") {
    delete(rootProject.buildDir)
  }
}
