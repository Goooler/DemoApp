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
  applyPlugins(Plugins.ktlint)

  configurations.all {
    resolutionStrategy.eachDependency {
      when (requested.group) {
        "androidx.appcompat" -> useVersion(appCompatVersion)
        "androidx.activity" -> useVersion(activityVersion)
        "androidx.collection" -> useVersion(collectionVersion)
        "androidx.core" -> useVersion(coreVersion)
        "androidx.fragment" -> useVersion(fragmentVersion)
        "androidx.lifecycle" -> {
          if (requested.name != "lifecycle-extensions")
            useVersion(lifecycleVersion)
        }
        "com.android.support" -> {
          if ("multidex" !in requested.name)
            useVersion(supportVersion)
        }
        "com.squareup.okhttp3" -> useVersion(okHttpVersion)
        else -> when {
          requested.name.startsWith("kotlinx-coroutines") ->
            useVersion(coroutinesVersion)
        }
      }
    }
  }

  tasks.matching {
    it.name.contains("transformClassesWithCom.alibaba.arouter")
  }.configureEach {
    notCompatibleWithConfigurationCache("https://github.com/alibaba/ARouter/issues/984")
  }
}

tasks {
  create<Delete>("clean") {
    val customFileTypes = fileTree(
      mapOf(
        "dir" to "$rootDir/gradle",
        "include" to arrayOf("*.log", "*.txt")
      )
    )
    delete(rootProject.buildDir, customFileTypes)
  }
  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }
}
