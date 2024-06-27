import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.bcv)
  alias(libs.plugins.mavenPublish)
}

android {
  namespace = "io.github.goooler.adapter.vp"

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

dependencies {
  api(libs.androidX.viewPager2)
}
