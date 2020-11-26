import java.util.concurrent.TimeUnit
import java.nio.charset.StandardCharsets

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

configurations.all {
    resolutionStrategy.run {
        cacheChangingModulesFor(0, TimeUnit.SECONDS)
        cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = StandardCharsets.UTF_8.toString()
}