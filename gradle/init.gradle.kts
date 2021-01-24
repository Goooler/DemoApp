allprojects {
  val aliyunMaven = "https://maven.aliyun.com/repository/public"
  repositories {
    all {
      (this as? MavenArtifactRepository)?.let {
        val url = it.url.toString()
        if (
          url.startsWith("https://repo1.maven.org/maven2") ||
          url.startsWith("https://jcenter.bintray.com/")
        ) {
          project.logger.lifecycle("Repository $url replaced by $aliyunMaven.")
          remove(it)
        }
      }
    }
    maven(aliyunMaven)
  }
}
