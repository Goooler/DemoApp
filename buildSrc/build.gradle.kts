plugins {
  `kotlin-dsl`
}

apply("../gradle/extra.gradle")

dependencies {
  implementation(rootProject.extra["androidPlugin"].toString())
  implementation(rootProject.extra["kotlinPlugin"].toString())
}
