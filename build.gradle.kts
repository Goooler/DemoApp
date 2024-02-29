import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.BasePlugin
import com.android.build.gradle.LibraryPlugin
import com.diffplug.gradle.spotless.SpotlessExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.google.devtools.ksp.gradle.KspGradleSubplugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.napt) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.cacheFix) apply false
}

allprojects {
  plugins.apply(rootProject.libs.plugins.detekt.get().pluginId)
  configure<DetektExtension> {
    config.from("$rootDir/detekt.yml")
    parallel = true
  }
  dependencies {
    "detektPlugins"(rootProject.libs.composeRules)
  }

  plugins.apply(rootProject.libs.plugins.spotless.get().pluginId)
  extensions.configure<SpotlessExtension> {
    kotlin {
      target("src/**/*.kt")
      ktlint(rootProject.libs.ktlint.get().version)
    }
    kotlinGradle {
      ktlint(rootProject.libs.ktlint.get().version)
    }
  }

  plugins.withType<BasePlugin> {
    plugins.apply(libs.plugins.kotlin.android.get().pluginId)
    plugins.apply(libs.plugins.cacheFix.get().pluginId)

    if (this is AppPlugin) {
      setupCommon()
    } else if (this is LibraryPlugin) {
      if (displayName.contains(":biz:") || name.startsWith("common")) setupCommon() else setupBase()
    }
  }
  plugins.withType<KspGradleSubplugin>().configureEach {
    configure<KspExtension> {
      arg("room.generateKotlin", "true")
      arg("room.incremental", "true")
    }
  }
  // Configure Java to use our chosen language level. Kotlin will automatically pick this up.
  // See https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
  plugins.withType<JavaBasePlugin>().configureEach {
    extensions.configure<JavaPluginExtension> {
      toolchain.languageVersion = JavaLanguageVersion.of(21)
    }
  }

  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      // Disable this flag due to https://github.com/Goooler/DemoApp/pull/437
//      allWarningsAsErrors = true
    }
  }
  tasks.withType<Test>().configureEach {
    useJUnitPlatform()
  }
  tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning = true
    enableStricterValidation = true
  }

  configurations.configureEach {
    // https://detekt.dev/docs/gettingstarted/gradle/#gradle-runtime-dependencies
    if (name == "detekt") return@configureEach
    resolutionStrategy.eachDependency {
      when (requested.group) {
        libs.androidX.appCompat.get().module.group -> useVersion(libs.androidX.appCompat.get().version.toString())
        libs.androidX.activity.compose.get().module.group -> useVersion(libs.androidX.activity.compose.get().version.toString())
        libs.androidX.collection.get().module.group -> useVersion(libs.androidX.collection.get().version.toString())
        libs.androidX.core.get().module.group -> useVersion(libs.androidX.core.get().version.toString())
        libs.androidX.fragment.get().module.group -> useVersion(libs.androidX.fragment.get().version.toString())
        libs.kotlin.junit5.get().module.group -> useVersion(libs.kotlin.junit5.get().version.toString())
        libs.kotlinX.coroutines.get().group -> when (requested.name) {
          libs.kotlinX.coroutines.get().name -> useVersion(libs.kotlinX.coroutines.get().version.toString())
          libs.kotlinX.immutable.get().name -> useVersion(libs.kotlinX.immutable.get().version.toString())
        }
        libs.square.okHttp.logInterceptor.get().module.group -> useVersion(libs.square.okHttp.logInterceptor.get().version.toString())
      }
    }
  }
}

fun <T : BaseExtension> Project.setupBase(block: T.() -> Unit) {
  extensions.configure<BaseExtension> {
    resourcePrefix = "${name}_"
    namespace = "io.goooler.demoapp.$name"
    compileSdkVersion(34)
    defaultConfig {
      minSdk = 21
      vectorDrawables.useSupportLibrary = true
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets.configureEach {
      java.srcDirs("src/$name/kotlin")
    }
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
    @Suppress("UNCHECKED_CAST")
    (this as T).block()
  }
}

fun Project.setupBase() {
  setupBase<BaseExtension> {}
}

fun Project.setupCommon() {
  setupBase<BaseExtension> {
    flavorDimensions("env")
    productFlavors {
      create("dev")
      create("prod")
    }
  }
}
