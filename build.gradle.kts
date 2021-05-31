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
    rootProject.extra["androidPlugin"].toString(),
    rootProject.extra["kotlinPlugin"].toString(),
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
  withType<Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  create<Delete>("clean") {
    delete(rootProject.buildDir)
  }
}
