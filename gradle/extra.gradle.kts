import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

rootProject.extra.run {
  set("androidGradlePlugin", "com.android.tools.build:gradle:4.2.0-rc01")
  set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}

repositories {
  google()
  mavenCentral()
  maven("https://maven.aliyun.com/repository/public")
  maven("https://jitpack.io")
}

configurations.all {
  resolutionStrategy {
    cacheChangingModulesFor(0, TimeUnit.SECONDS)
    cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = StandardCharsets.UTF_8.toString()
}
