import com.github.panpf.bintray.publish.PublishExtension

plugins {
  id(Plugins.androidLibrary)
}

setupBase()

dependencies {
  // architecture
  api(
    *Libs.coroutines,
    *Libs.lifecycle,
    Libs.core,
    Libs.annotation
  )

  // UI
  api(
    Libs.appCompat,
    Libs.fragment,
    Libs.material,
    Libs.constraintLayout,
    Libs.cardView
  )

  // network
  api(
    Libs.okHttp,
    Libs.retrofit
  )
}

apply(plugin = Plugins.bintrayPublish)

configure<PublishExtension> {
  userOrg = "goooler"
  groupId = "io.goooler.android"
  artifactId = "base"
  publishVersion = "1.0.0"
  desc = "Android project base module"
  website = "https://github.com/Goooler/DemoApp"
  repository = "https://github.com/Goooler/DemoApp"
}
