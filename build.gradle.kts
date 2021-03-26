import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
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
    Libs.detektPlugin,
    Libs.dependencyUpdatesPlugin
  )
}

allprojects {
  apply {
    from("${rootDir.path}/$extraScriptPath")
    plugin(Plugins.dependencyUpdate)
    plugin(Plugins.kotlinter)
    plugin(Plugins.detekt)
  }
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
  named<DependencyUpdatesTask>(GradleTask.DependencyUpdate.task) {
    rejectVersionIf {
      candidate.version.isStableVersion().not()
    }
  }
  withType<Detekt> {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  create(GradleTask.Clean.task, Delete::class) {
    delete(rootProject.buildDir)
  }
}
