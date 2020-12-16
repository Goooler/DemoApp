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
    maven(rootProject.extra.get("aliyunMaven").toString())
  }
  dependencies {
    classpath(rootProject.extra.get("androidGradlePlugin").toString())
    classpath(rootProject.extra.get("kotlinPlugin").toString())
    classpath(Libs.hiltPlugin)
    classpath(Libs.arouterPlugin)
    classpath(Libs.protobufPlugin)
    classpath(Libs.bintrayPublishPlugin)
  }
}

allprojects {
  apply {
    from("${rootDir.path}/$extraScriptPath")
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
  create(GradleTask.Clean.task, Delete::class.java) {
    rootProject.allprojects {
      delete(
        buildDir,
        fileTree(
          mapOf(
            "dir" to projectDir,
            "include" to arrayOf("*.log", "*.txt")
          )
        ),
        "${projectDir.path}/.classpath",
        "${projectDir.path}/.project",
        "${projectDir.path}/.settings"
      )
    }
  }
}
