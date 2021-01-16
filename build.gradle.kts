import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jmailen.gradle.kotlinter.support.ReporterType

plugins {
  id(Plugins.dependencyUpdate) version dependencyUpdateVersion
  id(Plugins.kotlinter) version kotlinterVersion
}

buildscript {
  apply(extraScriptPath)

  repositories {
    google()
    jcenter()
  }
  classpath(
    rootProject.getExtra("androidGradlePlugin"),
    rootProject.getExtra("kotlinPlugin"),
    Libs.hiltPlugin,
    Libs.arouterPlugin,
    Libs.protobufPlugin,
    Libs.bintrayPublishPlugin
  )
}

allprojects {
  apply {
    from("${rootDir.path}/$extraScriptPath")
    plugin(Plugins.dependencyUpdate)
    plugin(Plugins.kotlinter)
  }
  kotlinter {
    indentSize = 2
    reporters = arrayOf(ReporterType.html.name)
  }
}

tasks {
  named(GradleTask.DependencyUpdate.task, DependencyUpdatesTask::class) {
    rejectVersionIf {
      candidate.version.isStableVersion().not()
    }
  }
  create(GradleTask.Clean.task, Delete::class) {
    rootProject.allprojects {
      val customFileTypes = fileTree(
        mapOf(
          "dir" to projectDir,
          "include" to arrayOf("*.log", "*.txt")
        )
      )
      delete(buildDir, customFileTypes)
    }
  }
}
