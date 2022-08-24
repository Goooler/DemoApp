pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://jitpack.io")
  }
}

plugins {
  `gradle-enterprise`
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlways()
  }
}

val isCiBuild = System.getenv("GITHUB_ACTIONS") == "true"

buildCache {
  remote(HttpBuildCache::class.java) {
    setUrl("https://gradle.com/terms-of-service/cache/")
    isPush = isCiBuild
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
