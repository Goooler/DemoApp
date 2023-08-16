pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}

plugins {
  id("com.gradle.enterprise") version "3.14.1"
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
