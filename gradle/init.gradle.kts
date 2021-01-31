allprojects {
  val aliyunPublic = "https://maven.aliyun.com/repository/public"
  val aliyunPlugin = "https://maven.aliyun.com/repository/gradle-plugin"
  val mavenCenter = "https://repo.maven.apache.org/maven2"
  val jCenter = "https://jcenter.bintray.com"
  val central = "https://repo1.maven.org/maven2"
  val pluginPortal = "https://plugins.gradle.org/m2"
  repositories {
    all {
      (this as? MavenArtifactRepository)?.let {
        val url = it.url.toString()
        if (url.startsWith(central) || url.startsWith(mavenCenter) || url.startsWith(jCenter)) {
          project.logger.lifecycle("Repository $url replaced by $aliyunPublic.")
          remove(it)
        }
      }
    }
    maven(aliyunPublic)
  }
}
