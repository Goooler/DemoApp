plugins {
    `kotlin-dsl`
}

apply("../repositories.gradle.kts")

dependencies {
    implementation(rootProject.extra.get("androidPlugin").toString())
    implementation(rootProject.extra.get("kotlinPlugin").toString())
}