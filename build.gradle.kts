import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import dagger.hilt.android.plugin.HiltExtension
import org.gradle.kotlin.dsl.get
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.kapt) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ktlint) apply false
  alias(libs.plugins.moshiX) apply false
}

allprojects {
  apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
  configure<KtlintExtension> {
    version.set(rootProject.libs.versions.ktlint.get())
  }

  plugins.withId(rootProject.libs.plugins.hilt.get().pluginId) {
    configure<HiltExtension> {
      enableAggregatingTask = true
    }
  }

  configurations.all {
    resolutionStrategy.eachDependency {
      when (requested.group) {
        "com.android.support" -> {
          if ("multidex" !in requested.name) useVersion(libs.versions.support.get())
        }
        libs.androidX.appCompat.get().module.group -> useVersion(libs.versions.androidX.appCompat.get())
        libs.androidX.activity.compose.get().module.group -> useVersion(libs.versions.androidX.activity.get())
        libs.androidX.collection.get().module.group -> useVersion(libs.versions.androidX.collection.get())
        libs.androidX.core.get().module.group -> useVersion(libs.versions.androidX.core.get())
        libs.androidX.fragment.get().module.group -> useVersion(libs.versions.androidX.fragment.get())
        libs.gradlePlugin.kotlin.get().module.group -> useVersion(libs.versions.kotlin.get())
        libs.square.okHttp.logInterceptor.get().module.group -> useVersion(libs.versions.square.okHttp.get())
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

subprojects {
  plugins.withId(rootProject.libs.plugins.android.library.get().pluginId) {
    if (name.startsWith("biz-") || name.startsWith("common")) setupCommon() else setupBase()
  }
  plugins.withId(rootProject.libs.plugins.android.application.get().pluginId) {
    setupCommon()
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

val Project.shortName: String get() = name.removePrefix("biz-")

fun Project.setupBase(): BaseExtension {
  return extensions.getByName<BaseExtension>("android").apply {
    resourcePrefix = "${shortName}_"
    namespace = "io.goooler.demoapp.$shortName"
    compileSdkVersion(32)
    defaultConfig {
      minSdk = 21
      vectorDrawables.useSupportLibrary = true
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets.configureEach {
      java.srcDirs("src/$name/kotlin")
    }
    buildFeatures.buildConfig = false
    compileOptions.setDefaultJavaVersion(JavaVersion.VERSION_11)
    packagingOptions.resources.excludes += setOf(
      "**/*.proto",
      "**/*.bin",
      "**/*.java",
      "**/*.properties",
      "**/*.version",
      "**/*.*_module",
      "*.txt",
      "META-INF/services/**",
      "META-INF/com/**",
      "META-INF/licenses/**",
      "META-INF/AL2.0",
      "META-INF/LGPL2.1",
      "com/**",
      "kotlin/**",
      "kotlinx/**",
      "okhttp3/**",
      "google/**"
    )
    (this as? CommonExtension<*, *, *, *>)?.lint {
      abortOnError = true
    }
  }
}

fun Project.setupCommon(): BaseExtension = setupBase().apply {
  flavorDimensions("channel")
  productFlavors {
    create("dev")
    create("prod")
  }
}
