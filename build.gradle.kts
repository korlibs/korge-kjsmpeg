import korlibs.korge.gradle.*
import java.net.URL

plugins {
    alias(libs.plugins.korge)
}

korge {
    id = "org.korge.samples.mymodule"

// To enable all targets at once

    //targetAll()

// To enable targets based on properties/environment variables
    //targetDefault()

// To selectively enable targets

    targetJvm()
    targetJs()
    //targetIos()
    targetAndroid()
    serializationJson()
}

dependencies {
    add("commonMainApi", project(":deps"))
}

val sampleUrl = "https://github.com/korlibs/korge-kjsmpeg/releases/download/0.0.1/blade-runner-2049-360p.ts"
val sampleLocalPath = file("src/commonMain/resources/blade-runner-2049-360p.ts")
if (!sampleLocalPath.exists()) {
    sampleLocalPath.parentFile.mkdirs()
    println("Downloading $sampleUrl -> $sampleLocalPath")
    sampleLocalPath.writeBytes(URL(sampleUrl).readBytes())
}
