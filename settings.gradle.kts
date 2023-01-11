pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    maven("https://storage.googleapis.com/r8-releases/raw")
  }

  buildscript {
    dependencies {
      classpath("com.android.tools:r8:4.0.48")
    }
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()

    // Need to use the 'dev' Compose Compiler for Kotlin 1.8.0 support
    maven("https://androidx.dev/storage/compose-compiler/repository/")
  }
}

plugins {
  id("com.gradle.enterprise") version "3.12.2"
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlways()
  }
}

include(
  // App
  ":app",
  // Common
  ":base",
  ":common",
  ":adapter",
  // Biz
  ":biz:login",
  ":biz:main",
  ":biz:detail",
  ":biz:web",
  ":biz:obsolete",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
