plugins {
  `kotlin-dsl`
}

apply("../gradle/extra.gradle")

dependencies {
  implementation(rootProject.extra["androidGradlePlugin"].toString())
  implementation(rootProject.extra["kotlinPlugin"].toString())
}