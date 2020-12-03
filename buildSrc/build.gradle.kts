plugins {
    `kotlin-dsl`
}

apply("extra.gradle.kts")

dependencies {
    implementation(rootProject.extra.get("androidPlugin").toString())
    implementation(rootProject.extra.get("kotlinPlugin").toString())
}