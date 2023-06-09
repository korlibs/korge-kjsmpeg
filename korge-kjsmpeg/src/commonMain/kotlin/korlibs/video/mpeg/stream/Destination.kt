package korlibs.video.mpeg.stream

import korlibs.memory.Uint8ClampedBuffer

interface Destination {
}

interface AudioDestination : Destination {
    fun play(rate: Int, left: FloatArray, right: FloatArray)
    val enqueuedTime: Double
}

interface VideoDestination : Destination {
    fun render(Y: Uint8ClampedBuffer, Cr: Uint8ClampedBuffer, Cb: Uint8ClampedBuffer, v: Boolean)
    fun resize(width: Int, height: Int)
}
