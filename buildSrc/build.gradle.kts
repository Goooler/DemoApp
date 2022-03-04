plugins {
  `kotlin-dsl`
}

configurations.all {
  resolutionStrategy.eachDependency {
    if (requested.name == "javapoet") {
      useVersion("1.13.0")
    }
  }
}

repositories {
  google()
  mavenCentral()
}

dependencies {
  implementation(libs.gradlePlugin.android)
  implementation(libs.gradlePlugin.kotlin)
}
