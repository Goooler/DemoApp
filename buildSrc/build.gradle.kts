plugins {
  `kotlin-dsl`
  `kotlin-dsl-precompiled-script-plugins`
}

apply("extra.gradle.kts")

dependencies {
  implementation(rootProject.extra["androidGradlePlugin"].toString())
  implementation(rootProject.extra["kotlinPlugin"].toString())
}