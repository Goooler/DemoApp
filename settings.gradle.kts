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

include(
  // App
  ":app",
  ":obsolete",
  // Common
  ":base",
  ":common",
  ":adapter",
  // Biz
  ":biz:login",
  ":biz:main",
  ":biz:detail",
  ":biz:web"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
