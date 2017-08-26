package hy.flappybird.sprite

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import hy.flappybird.BirdApp
import java.util.*

/**
 * Created time : 2017/8/25 14:52.
 *  水管障碍物 精灵类
 *  1.上下水管之中间距一定距离
 *  2.水管以一定速度向左移动
 *
 * @author HY
 */
class Obstacle(private val downPillar: Bitmap, private val upPillar: Bitmap, bottom: Int) {
    //水管距离
    private val dis = 350
    private val minHeight = 350
    private val screenHeight = BirdApp.screenHeight
    private val screenWidth = BirdApp.screenWidth

    val width = downPillar.width
    private val height = downPillar.height

    private val realHeight = screenHeight - bottom
    private val random = Random()
    var x = screenWidth

    private val speed = 10

    val downRectF = RectF()
    val upRectF = RectF()

    private var downHeight: Int = random.nextInt(realHeight - 2*minHeight-dis) + minHeight
    private var upHeight: Int = realHeight - dis - downHeight

    private val distance = (screenWidth - width) / 2

    private fun reset() {
        x = screenWidth
        downHeight = random.nextInt(realHeight - 2*minHeight-dis) + minHeight
        upHeight = realHeight - dis - downHeight
    }

    fun draw(canvas: Canvas, paint: Paint) {
        downRectF.set(x.toFloat(), downHeight - height.toFloat(), x.toFloat() + width, downHeight.toFloat())
        upRectF.set(x.toFloat(), realHeight.toFloat() - upHeight, x.toFloat() + width, height - upHeight + realHeight.toFloat())

        canvas.drawBitmap(downPillar, null, downRectF, paint)
        canvas.drawBitmap(upPillar, null, upRectF, paint)
    }

    private var isFirst = true

    var isDead=false

    fun logic() {
        if (!isDead){
            x -= speed

            if (x + width <= 0) {
                reset()
            }

            if ((screenWidth - x - width) in distance..(distance + 10) && isFirst) {
                isFirst = false
                _notice?.invoke()
            }
        }

    }

    fun reload() {
        isFirst = true
        isDead=false
        reset()
    }

    private var _notice: (() -> Unit)? = null

    fun notice(notice: () -> Unit) {
        _notice = notice
    }
}