import com.github.panpf.bintray.publish.PublishExtension

applyPlugin(Plugins.androidLibrary)

setupBase()

dependencies {
  api(
    // architecture
    *Libs.coroutines,
    *Libs.lifecycle,
    Libs.core,
    Libs.annotation,

    // UI
    Libs.appCompat,
    Libs.fragment,
    Libs.material,
    Libs.constraintLayout,
    Libs.cardView,

    // network
    Libs.okHttp,
    Libs.retrofit
  )
}

applyPlugin(Plugins.bintrayPublish)

configure<PublishExtension> {
  userOrg = "goooler"
  groupId = "io.goooler.android"
  artifactId = "base"
  publishVersion = "1.0.0"
  desc = "Android project base module"
  website = "https://github.com/Goooler/DemoApp"
  repository = "https://github.com/Goooler/DemoApp"
}
