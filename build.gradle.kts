import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jmailen.gradle.kotlinter.KotlinterExtension
import org.jmailen.gradle.kotlinter.support.ReporterType

buildscript {
  apply(extraScriptPath)

  repositories {
    google()
    gradlePluginPortal()
  }
  classpaths(
    rootProject.getExtra("androidGradlePlugin"),
    rootProject.getExtra("kotlinPlugin"),
    Libs.hiltPlugin,
    Libs.arouterPlugin,
    Libs.kotlinterPlugin,
    Libs.detektPlugin
  )
}

allprojects {
  apply("${rootDir.path}/$extraScriptPath")
  applyPlugins(
    Plugins.kotlinter,
    Plugins.detekt,
    Plugins.picCompress,
    Plugins.customTrans
  )
  configure<KotlinterExtension> {
    indentSize = 2
    reporters = arrayOf(ReporterType.html.name)
  }
  configure<DetektExtension> {
    parallel = true
    buildUponDefaultConfig = true
    reports.html.enabled = true
  }
}

tasks {
  withType<Detekt> {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  create<Delete>(GradleTask.Clean.task) {
    delete(rootProject.buildDir)
  }
}
