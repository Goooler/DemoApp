import com.github.panpf.bintray.publish.PublishExtension

applyPlugins(Plugins.androidLibrary) {
  setupBase()
}

dependencies {
  apis(
    // architecture
    *Libs.coroutines,
    *Libs.lifecycle,
    Libs.core,
    Libs.annotation,

    // UI
    Libs.appCompat,
    Libs.fragment,

    // network
    Libs.okHttp,
    Libs.retrofit
  )
}

applyPlugins(Plugins.bintrayPublish) {
  configure<PublishExtension> {
    userOrg = "goooler"
    groupId = "io.goooler.android"
    artifactId = "base"
    publishVersion = "1.0.0"
    desc = "Android project base module"
    website = "https://github.com/Goooler/DemoApp"
    repository = "https://github.com/Goooler/DemoApp"
  }
}
