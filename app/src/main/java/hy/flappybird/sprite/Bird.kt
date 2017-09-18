package hy.flappybird.sprite

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import hy.flappybird.BirdApp
import java.util.*

/**
 * Created time : 2017/8/25 9:10.
 * 小鸟精灵类，小鸟的逻辑和绘制
 *
 * @author HY
 */

class Bird(private val bitmaps: Vector<Bitmap>) {

    private val bird_width = bitmaps[0].width
    private val bird_height = bitmaps[0].height

    var readySize: Int = 0

    private val x = (BirdApp.screenWidth - bird_width) / 2
    private var y = (BirdApp.screenHeight - bird_height) / 2

    val tempY = y
    private var upTmpY = tempY

    private val dis = 25 * BirdApp.screenHeight / 1920
    private val jump = 75 * BirdApp.screenHeight / 1920//todo

    private var i = 0

    private var count = 0

    private var mode = BirdMode.UP

    private var posMode = PosMode.MENU

    var earthHeight = 0


    init {
        upBirds.clear()
        downBirds.clear()
        deadBirds.clear()
        for (bitmap in bitmaps) {
            val matrix = Matrix()
            matrix.postRotate(-20f)
            upBirds.add(Bitmap.createBitmap(bitmap, 0, 0, bird_width, bird_height, matrix, true))
            matrix.postRotate(20f)
            downBirds.add(Bitmap.createBitmap(bitmap, 0, 0, bird_width, bird_height, matrix, true))
            matrix.postRotate(90f)
            deadBirds.add(Bitmap.createBitmap(bitmap, 0, 0, bird_width, bird_height, matrix, true))
        }

    }


    fun changeMode(posMode: PosMode) {
        this.posMode = posMode
        i = 0
        count = 0

        if (posMode == PosMode.PRESS) {
            upTmpY = y
        }
    }

    fun draw(canvas: Canvas, paint: Paint) {
        when (posMode) {
            PosMode.MENU -> canvas.drawBitmap(bitmaps[i % 3], x.toFloat(), y.toFloat(), paint)
            PosMode.PRESS -> {

//                canvas.save()
//
//                canvas.rotate(-45f,BirdApp.screenWidth/2f,BirdApp.screenHeight/2f)
                //todo
                canvas.drawBitmap(upBirds[i % 3], x - readySize.toFloat(), y.toFloat(), paint)

//                canvas.rotate(45f,BirdApp.screenWidth/2f,BirdApp.screenHeight/2f)
//
//                canvas.restore()
            }
            PosMode.UP -> {
//                canvas.save()
//
//                canvas.rotate(90f,BirdApp.screenWidth/2f,BirdApp.screenHeight/2f)
                //todo
                canvas.drawBitmap(downBirds[i % 3], x - readySize.toFloat(), y.toFloat(), paint)

//                canvas.rotate(-90f,BirdApp.screenWidth/2f,BirdApp.screenHeight/2f)
//
//                canvas.restore()
            }
            PosMode.READY -> {
                canvas.drawBitmap(bitmaps[i % 3], x - readySize.toFloat(), y.toFloat(), paint)
            }
            Bird.PosMode.DEAD -> {
                canvas.drawBitmap(deadBirds[i % 3], x - readySize.toFloat(), y.toFloat(), paint)
            }
        }

    }

    fun logic() {
        when (posMode) {
            PosMode.MENU -> {
                if (y <= tempY - dis) {
                    mode = BirdMode.DOWN
                } else if (y >= tempY + dis) {
                    mode = BirdMode.UP
                }

                if (mode == BirdMode.UP) {
                    y -= 2
                } else {
                    y += 2
                }
                if (count % 5 == 0)
                    i++
                count++
            }
            PosMode.PRESS -> {
                y -= 15

                if (y <= upTmpY - jump) {
                    changeMode(PosMode.UP)
                }

                if (count % 5 == 0)
                    i++
                count++
            }
            PosMode.UP -> {
                //todo
                y += 14

                if (count % 5 == 0)
                    i++
                count++
            }
            PosMode.READY -> {
                if (y <= tempY - dis) {
                    mode = BirdMode.DOWN
                } else if (y >= tempY + dis) {
                    mode = BirdMode.UP
                }

                if (mode == BirdMode.UP) {
                    y -= 2
                } else {
                    y += 2
                }
                if (count % 3 == 0)
                    i++
                count++
            }
            Bird.PosMode.DEAD -> {
                if (y + bird_height <= BirdApp.screenHeight - earthHeight) {
                    y += 15
                } else {
                    _onDead?.invoke()
                }
            }
        }

    }

    fun isCollisionWith(obstacle: Obstacle): Boolean {
        val x1 = obstacle.x
        val x2 = x1 + obstacle.width

        if (x - readySize <= x2 && x - readySize + bird_width >= x1 && (y <= obstacle.downRectF.bottom || y + bird_height >= obstacle.upRectF.top)) {
            return true
        }
        return false
    }

    fun isCross(obstacle: Obstacle): Boolean {
        val x1 = obstacle.x + obstacle.width
        if (x - readySize > x1 && x - readySize < x1 + 10) {
            return true
        }
        return false
    }

    fun isCollisionWithEarth(): Boolean {
        if (y >= BirdApp.screenHeight - earthHeight) {
            return true
        }
        return false
    }

    private var _onDead: (() -> Unit)? = null

    fun dead(onDead: () -> Unit) {
        _onDead = onDead
    }

    enum class BirdMode {
        UP, DOWN
    }

    enum class PosMode {
        MENU, //菜单
        READY, //准备
        PRESS, //手指按下
        UP, //手指抬起
        DEAD//死亡
    }

    companion object {
        private val upBirds = Vector<Bitmap>()
        private val downBirds = Vector<Bitmap>()
        private val deadBirds = Vector<Bitmap>()
    }

}