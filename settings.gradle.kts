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
    maven("https://oss.sonatype.org/content/repositories/snapshots")
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
  ":obsolete"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
