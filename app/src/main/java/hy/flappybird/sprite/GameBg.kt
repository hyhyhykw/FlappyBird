package hy.flappybird.sprite

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import hy.flappybird.BirdApp

/**
 * Created time : 2017/8/23 15:29.
 * 精灵类 游戏背景绘制
 *
 * @author HY
 */
class GameBg(bitmap: Bitmap) {
    //需要两张相同的图片首尾相接，形成循环运动的效果
    private val bm1 = bitmap
    private val bm2 = bitmap

    private var bm1x: Int = 0
    private var bm2x: Int = bm1x + BirdApp.screenWidth

     val bm1y: Int = BirdApp.screenHeight - bitmap.height

    private val rectF1 = RectF()
    private val rectF2 = RectF()
    //背景滚动速度
    private val speed = 10

    var isDead=false

    //绘制游戏背景
    fun draw(canvas: Canvas, paint: Paint) {
        //这里为了固定底部的尺寸 使用RectF
        //防止占用过多内存，将两个RectF作为常量，使用set方法重新设置数值
        rectF1.set(bm1x.toFloat(), bm1y.toFloat(), bm1x + BirdApp.screenWidth.toFloat(), BirdApp.screenHeight.toFloat())
        rectF2.set(bm2x.toFloat(), bm1y.toFloat(), bm2x + BirdApp.screenWidth.toFloat(), BirdApp.screenHeight.toFloat())

        canvas.drawBitmap(bm1, null, rectF1, paint)
        canvas.drawBitmap(bm2, null, rectF2, paint)
    }

    fun reset(){
        isDead=false
        bm1x=0
        bm2x=bm1x + BirdApp.screenWidth
    }

    //游戏背景的逻辑
    fun logic() {
        if (!isDead){
            bm1x -= speed
            bm2x -= speed

            //当第一张图片的X坐标超出屏幕，
            //立即将其坐标设置到第二张图的右边
            if (bm1x + BirdApp.screenWidth < 0) {
                bm1x = bm2x + BirdApp.screenWidth
            }
            //当第二张图片的X坐标超出屏幕，
            //立即将其坐标设置到第一张图的右边
            if (bm2x + BirdApp.screenWidth < 0) {
                bm2x = bm1x + BirdApp.screenWidth
            }
        }

    }
}