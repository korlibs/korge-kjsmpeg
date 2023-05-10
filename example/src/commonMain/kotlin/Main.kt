import korlibs.image.bitmap.Bitmaps
import korlibs.image.bitmap.slice
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.Korge
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.korge.view.SContainer
import korlibs.korge.view.image
import korlibs.korge.view.text
import korlibs.time.DateTime
import korlibs.time.seconds
import korlibs.video.mpeg.JSMpegPlayer

suspend fun main() = Korge {
    sceneContainer().changeTo({ MainMyModuleScene() })
}

// WIP: https://github.com/phoboslab/jsmpeg/blob/master/src/player.js
class MainMyModuleScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val image = image(Bitmaps.transparent)
        val statusText = text("")
        val localFile = resourcesVfs["blade-runner-2049-360p.ts"]
        //val localFile = applicationDataVfs["blade-runner-2049-360p.ts"]
        //val url = "https://jsmpeg.com/blade-runner-2049-360p.ts"
        //if (!localFile.exists()) {
        //    statusText.text = "Downloading... $url to $localFile"
        //    localFile
        //        .writeBytes(UrlVfs(url).readBytes())
        //}
        statusText.text = "Playing... $localFile"

        val data = localFile.openInputStream()
        //val data = resourcesVfs["blade-runner-2049-1080p.ts.mpeg"].openInputStream()
        val player = JSMpegPlayer(views.coroutineContext)
        player.audioStream.start()

        player.onDecodedVideoFrame.add {
            it.bitmap.lock {}

            //println("player.video.decodedTime=${player.video.decodedTime}, player.demuxer.currentTime=${player.demuxer.currentTime}, player.lastVideoTime=${player.lastVideoTime}")
            image.bitmap = it.bitmap.slice()
        }

        player.setStream(data)
        //addUpdater { player.frameSync() }

        launchImmediately(views.coroutineContext) {
            var startTime = DateTime.now()
            while (true) {
                val decodeTime = player.video.decodedTime.seconds
                val currentTime = DateTime.now() - startTime
                val diff = (decodeTime - currentTime)
                if (!player.frame()) break
                //if (diff > 0.seconds) delay(diff)
                println("currentTime=$currentTime decodeTime=$decodeTime")
            }
        }

        //text(MyModule.TEXT)
    }
}