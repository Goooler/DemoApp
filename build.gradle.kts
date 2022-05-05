import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import dagger.hilt.android.plugin.HiltExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
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

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_11.toString()
      // https://youtrack.jetbrains.com/issue/KT-41985
      @Suppress("SuspiciousCollectionReassignment")
      freeCompilerArgs += listOf(
        "-progressive",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xcontext-receivers",
        "-Xjvm-default=all",
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
      )
    }
  }
  tasks.withType<Test> {
    useJUnitPlatform()
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
}

subprojects {
  plugins.withId(rootProject.libs.plugins.android.library.get().pluginId) {
    if (displayName.contains(":biz:") || name.startsWith("common")) setupCommon() else setupBase()
  }
  plugins.withId(rootProject.libs.plugins.android.application.get().pluginId) {
    setupCommon()
  }
  plugins.withId(rootProject.libs.plugins.hilt.get().pluginId) {
    configure<HiltExtension> {
      enableAggregatingTask = true
    }
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

fun Project.setupBase(): BaseExtension {
  return extensions.getByName<BaseExtension>("android").apply {
    resourcePrefix = "${name}_"
    namespace = "io.goooler.demoapp.$name"
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
      "com/**",
      "google/**",
      "kotlin/**",
      "kotlinx/**",
      "okhttp3/**",
      "META-INF/services/**",
      "META-INF/com/**",
      "META-INF/licenses/**",
      "META-INF/AL2.0",
      "META-INF/LGPL2.1",
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
