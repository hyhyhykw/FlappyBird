package hy.flappybird.sprite

import java.util.*

/**
 * Created time : 2017/8/23 12:51.
 * @author HY
 */
data class Stripe(val frames: ArrayList<Frame>)


data class Frame(val filename: String,
                 val frame: FrameBean,
                 val spriteSourceSize: FrameBean,
                 val sourceSize: SourceSize,
                 val rotated: Boolean = false,
                 val trimmed: Boolean = false)

data class FrameBean(val x: Int, val y: Int, val w: Int, val h: Int)
data class SourceSize(val w: Int, val h: Int)
