import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.bcv)
  alias(libs.plugins.napt)
  alias(libs.plugins.mavenPublish)
  signing
}

android {
  namespace = "io.github.goooler.adapter.rv"
  buildFeatures.dataBinding = true

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

kotlin {
  explicitApi()
  compilerOptions {
    jvmTarget = JvmTarget.JVM_1_8
  }
}

mavenPublishing {
  signAllPublications()
}

signing {
  isRequired = !version.toString().endsWith("-SNAPSHOT")
}

dependencies {
  api(libs.androidX.collection)
  api(libs.androidX.paging)
  api(libs.androidX.recyclerView)
}
