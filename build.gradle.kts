import java.nio.charset.StandardCharsets

buildscript {
    apply("repositories.gradle.kts")

    repositories {
        google()
        maven("https://maven.aliyun.com/repository/public")
    }
    dependencies {
        classpath(rootProject.extra.get("androidPlugin").toString())
        classpath(rootProject.extra.get("kotlinPlugin").toString())
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
                    "include" to cleanFileTypes
                )
            )
        )
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = StandardCharsets.UTF_8.toString()
}