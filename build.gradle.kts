import java.nio.charset.StandardCharsets

buildscript {
    apply("repositories.gradle.kts")

    repositories {
        google()
        maven(rootProject.extra.get("aliyunMaven").toString())
    }
    dependencies {
        classpath(rootProject.extra.get("androidPlugin").toString())
        classpath(rootProject.extra.get("kotlinPlugin").toString())
        classpath(Plugins.protobufPlugin)
    }
}

allprojects {
    apply("${rootDir.path}/repositories.gradle.kts")
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
    tasks.withType(JavaCompile::class) {
        options.encoding = StandardCharsets.UTF_8.toString()
    }
}

task("clean", Delete::class) {
    rootProject.allprojects {
        delete(buildDir)
        delete(
            fileTree(
                mapOf(
                    "dir" to projectDir,
                    "include" to cleanFileTypes
                )
            )
        )
    }
}