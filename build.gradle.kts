import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jmailen.gradle.kotlinter.support.ReporterType

plugins {
    id(Plugins.gradleVersionsPlugin) version gradleVersionsPluginVersion
    id(Plugins.kotlinterPlugin) version kotlinterPluginVersion
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
    }
}

allprojects {
    apply {
        from("${rootDir.path}/$extraScriptPath")
        plugin(Plugins.kotlinterPlugin)
    }
    kotlinter {
        indentSize = 4
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
