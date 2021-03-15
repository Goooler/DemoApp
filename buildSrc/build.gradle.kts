plugins {
  `kotlin-dsl`
}

apply("../gradle/extra.gradle.kts")

dependencies {
  implementation(rootProject.extra["androidGradlePlugin"].toString())
  implementation(rootProject.extra["kotlinPlugin"].toString())
}