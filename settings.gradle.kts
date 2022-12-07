pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  id("com.gradle.enterprise") version "3.11.4"
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

includeBuild("demo") {
  dependencySubstitution {
    substitute(module("demo:common")).using(project(":common"))
    substitute(module("demo:biz.login")).using(project(":biz:login"))
    substitute(module("demo:biz.main")).using(project(":biz:main"))
    substitute(module("demo:biz.detail")).using(project(":biz:detail"))
    substitute(module("demo:biz.web")).using(project(":biz:web"))
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
