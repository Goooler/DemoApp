import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

rootProject.extra.run {
  set("androidGradlePlugin", "com.android.tools.build:gradle:4.2.0-beta03")
  set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21-2")
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