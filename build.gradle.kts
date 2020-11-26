buildscript {
    apply("repositories.gradle.kts")

    repositories {
        google()
        maven(rootProject.extra.get("aliyunMaven").toString())
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
    apply("${rootDir.path}/repositories.gradle.kts")
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