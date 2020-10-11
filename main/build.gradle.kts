import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.protobuf")
}

setupCommon().run {
    resourcePrefix(ResourcePrefix.main)
    defaultConfig {
        versionNameSuffix = VersionNameSuffix.main
    }
    setupFlavors()
}

kapt {
    kaptCommon()
}

protobuf {
    protoc {
        artifact = Libs.protoc
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.adapter))

    implementation(Libs.protobufLite)
    kapt(Libs.arouterKapt)
    kapt(Libs.objectBoxKapt)
}