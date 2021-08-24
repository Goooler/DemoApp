import java.nio.charset.StandardCharsets

rootProject.extra.run {
  set("androidPlugin", "com.android.tools.build:gradle:7.0.1")
  set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
}

repositories {
  google()
  mavenCentral()
  maven("https://maven.aliyun.com/repository/public")
  maven("https://jitpack.io")
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = StandardCharsets.UTF_8.toString()
}
