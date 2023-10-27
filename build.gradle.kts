import androidx.room.gradle.RoomExtension
import androidx.room.gradle.RoomGradlePlugin
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.BasePlugin
import com.android.build.gradle.LibraryPlugin
import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.androidX.room) apply false
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

  plugins.apply(rootProject.libs.plugins.spotless.get().pluginId)
  extensions.configure<SpotlessExtension> {
    kotlin {
      ktlint("1.0.1")
      target("src/**/*.kt")
    }
    kotlinGradle {
      ktlint("1.0.1")
      target("*.kts")
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

  plugins.withType<RoomGradlePlugin>().configureEach {
    extensions.configure<RoomExtension> {
      schemaDirectory("$projectDir/schemas/")
    }
  }

  // Configure Java to use our chosen language level. Kotlin will automatically pick this up.
  // See https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
  plugins.withType<JavaBasePlugin>().configureEach {
    extensions.configure<JavaPluginExtension> {
      toolchain {
        languageVersion = JavaLanguageVersion.of(17)
      }
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
        libs.square.okHttp.logInterceptor.get().module.group -> useVersion(libs.versions.square.okHttp.get())
        else -> when {
          requested.name.startsWith("kotlinx-coroutines") ->
            useVersion(libs.versions.coroutines.get())
        }
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
