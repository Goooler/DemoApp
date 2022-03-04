import dagger.hilt.android.plugin.HiltExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

buildscript {
  repositories {
    google()
    gradlePluginPortal()
  }

  dependencies {
    classpath(libs.gradlePlugin.android)
    classpath(libs.gradlePlugin.kotlin)
    classpath(libs.gradlePlugin.hilt)
    classpath(libs.gradlePlugin.ktlint)
    classpath(libs.gradlePlugin.moshiX)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://jitpack.io")
  }

  apply(plugin = Plugins.ktlint)
  configure<KtlintExtension> {
    version.set(ktlintVersion)
  }

  plugins.withId(Plugins.hilt) {
    configure<HiltExtension> {
      enableAggregatingTask = true
    }
  }

  configurations.all {
    resolutionStrategy.eachDependency {
      when (requested.group) {
        "org.jetbrains.kotlin" -> useVersion(libs.versions.kotlin.get())
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
          requested.name == "javapoet" -> useVersion("1.13.0")
        }
      }
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
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
