import korlibs.korge.gradle.*
import java.net.URL

plugins {
    //alias(libs.plugins.korge)
    //id("com.soywiz.korge") version "999.0.0.999"
    id("com.soywiz.korge") version "4.0.0"
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
    targetDesktop()
    targetDesktopCross()
    targetIos()
    targetAndroid()
    serializationJson()
}

dependencies {
    add("commonMainApi", project(":deps"))
}

val sampleUrl = "https://jsmpeg.com/blade-runner-2049-360p.ts"
val sampleLocalPath = file("src/commonMain/resources/blade-runner-2049-360p.ts")
if (!sampleLocalPath.exists()) {
    println("Downloading $sampleUrl -> $sampleLocalPath")
    sampleLocalPath.writeBytes(URL(sampleUrl).readBytes())
}
