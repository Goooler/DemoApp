rootProject.extra.apply {
    set("androidPlugin", "com.android.tools.build:gradle:4.1.0-rc03")
    set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
}

repositories {
    google()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://jitpack.io")
}