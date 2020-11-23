rootProject.extra.apply {
    set("androidPlugin", "com.android.tools.build:gradle:4.1.1")
    set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    set("aliyunMaven", "https://maven.aliyun.com/repository/public")
    set("jitpackMaven", "https://jitpack.io")
}

repositories {
    google()
    maven(rootProject.extra.get("aliyunMaven").toString())
    maven(rootProject.extra.get("jitpackMaven").toString())
}