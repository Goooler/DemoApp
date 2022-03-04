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
    version.set(rootProject.libs.versions.ktlint.get())
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
        "androidx.appcompat" -> useVersion(libs.versions.androidX.appCompat.get())
        "androidx.activity" -> useVersion(libs.versions.androidX.activity.get())
        "androidx.collection" -> useVersion(libs.versions.androidX.collection.get())
        "androidx.core" -> useVersion(libs.versions.androidX.core.get())
        "androidx.fragment" -> useVersion(libs.versions.androidX.fragment.get())
        "androidx.lifecycle" -> {
          if (requested.name != "lifecycle-extensions")
            useVersion(libs.versions.androidX.lifecycle.get())
        }
        "com.android.support" -> {
          if ("multidex" !in requested.name)
            useVersion(libs.versions.support.get())
        }
        "com.squareup.okhttp3" -> useVersion(libs.versions.square.okHttp.get())
        else -> when {
          requested.name.startsWith("kotlinx-coroutines") ->
            useVersion(libs.versions.coroutines.get())
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
