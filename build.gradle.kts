import java.nio.charset.StandardCharsets

buildscript {
    repositories {
        google()
        maven(aliyunMaven)
    }
    dependencies {
        classpath(Libs.gradlePlugin)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.protobufPlugin)
    }

    configurations.all {
        // check for updates every build
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
}

allprojects {
    repositories {
        google()
        maven(aliyunMaven)
        maven(jitpackMaven)
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

tasks.withType(JavaCompile::class) {
    options.encoding = StandardCharsets.UTF_8.toString()
}