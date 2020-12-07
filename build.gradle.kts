import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id(Plugins.gradleVersionsPlugin) version gradleVersionsPluginVersion
}

buildscript {
    apply(extraScriptPath)

    repositories {
        google()
        maven(rootProject.extra.get("aliyunMaven").toString())
        gradlePluginPortal()
    }
    dependencies {
        classpath(rootProject.extra.get("androidPlugin").toString())
        classpath(rootProject.extra.get("kotlinPlugin").toString())
        classpath(Libs.hiltPlugin)
        classpath(Libs.arouterPlugin)
        classpath(Libs.protobufPlugin)
    }
}

allprojects {
    apply("${rootDir.path}/$extraScriptPath")
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class).configure {
    rejectVersionIf {
        candidate.version.isNotStable()
    }
}

task("clean", Delete::class) {
    rootProject.allprojects {
        delete(buildDir)
        delete(
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