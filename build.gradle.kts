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
    Libs.ktlintPlugin
  )
}

allprojects {
  apply("$rootDir/$extraScriptPath")
  applyPlugins(
    Plugins.ktlint,
    Plugins.picCompress,
    Plugins.customTrans
  )
}

tasks {
  create<Delete>("clean") {
    delete(rootProject.buildDir)
  }
  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }
}
