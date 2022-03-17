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
  ":app",
  ":base",
  ":common",
  ":login",
  ":main",
  ":detail",
  ":web",
  ":adapter",
  ":Obsolete"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
