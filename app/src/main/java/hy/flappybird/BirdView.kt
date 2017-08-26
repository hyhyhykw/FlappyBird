package hy.flappybird

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import hy.flappybird.sprite.Bird
import hy.flappybird.sprite.GameBg
import hy.flappybird.sprite.Obstacle
import hy.flappybird.utils.Counter
import hy.flappybird.utils.e
import hy.flappybird.utils.screenHeight
import hy.flappybird.utils.screenWidth
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created time : 2017/8/22 18:20.
 * @author HY
 */
class BirdView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback, Runnable {

    //游戏中运动的背景
    private val gameBg: GameBg
    //运动背景的图片
    private val bgBitmap: Bitmap
    //绘制游戏的线程
    private val thread: Thread
    //画笔
    private val paint: Paint
    private val sfh: SurfaceHolder?
    //画布
    private var canvas: Canvas? = null
    //线程同步
    private var mDrawFlag = true
    //停止绘制
    private var isStop = false
    //背景 白天
    private var bm1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.background_day)
    //背景 夜晚
    private var bm2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.background_night)

    private var bm = bm1

    private val loadBm = BitmapFactory.decodeResource(resources, R.drawable.icon_loading)

    private val rectF = RectF()
    //小鸟的不同形态的图片
    private val bird_blue_up = BitmapFactory.decodeResource(resources, R.drawable.blue_bird_up)
    private val bird_blue_middle = BitmapFactory.decodeResource(resources, R.drawable.blue_bird_middle)
    private val bird_blue_down = BitmapFactory.decodeResource(resources, R.drawable.blue_bird_down)

    private val bird_red_up = BitmapFactory.decodeResource(resources, R.drawable.red_bird_up)
    private val bird_red_middle = BitmapFactory.decodeResource(resources, R.drawable.red_bird_middle)
    private val bird_red_down = BitmapFactory.decodeResource(resources, R.drawable.red_bird_down)

    private val bird_yellow_up = BitmapFactory.decodeResource(resources, R.drawable.yellow_bird_up)
    private val bird_yellow_middle = BitmapFactory.decodeResource(resources, R.drawable.yellow_bird_middle)
    private val bird_yellow_down = BitmapFactory.decodeResource(resources, R.drawable.yellow_bird_down)

    private val blueBirds = Vector<Bitmap>()
    //三种颜色的小鸟的集合
    private val redBirds = Vector<Bitmap>()
    private val yellowBirds = Vector<Bitmap>()
    //用于随机获取背景和小鸟的工具
    private val random = Random()

    private var mBird: Bird
    //菜单里面标题的图片和尺寸
    private val menu_title = BitmapFactory.decodeResource(resources, R.drawable.icon_title)
    private val menuTitleRectF = RectF()

    //菜单底部按钮的图片 开始按钮 和 排名按钮
    private val startIcon = BitmapFactory.decodeResource(resources, R.drawable.icon_start)
    private val rankIcon = BitmapFactory.decodeResource(resources, R.drawable.icon_list)

    //准备阶段中心的图
    private val loadCenter = BitmapFactory.decodeResource(resources, R.drawable.load_center)

    private val loadCenterWidth = loadCenter.width
    private val loadCenterHeight = loadCenter.height
    //水管(障碍物) 图片
    private val downPillar = BitmapFactory.decodeResource(resources, R.drawable.pillar_down)
    private val upPillar = BitmapFactory.decodeResource(resources, R.drawable.pillar_up)

    private val obs1: Obstacle
    private val obs2: Obstacle

    //准备标题图片
    private val readyIcon = BitmapFactory.decodeResource(resources, R.drawable.icon_ready)
    //数字图片
    private val zero = BitmapFactory.decodeResource(resources, R.drawable.icon_zero)
    private val one = BitmapFactory.decodeResource(resources, R.drawable.icon_one)
    private val two = BitmapFactory.decodeResource(resources, R.drawable.icon_two)
    private val three = BitmapFactory.decodeResource(resources, R.drawable.icon_three)
    private val four = BitmapFactory.decodeResource(resources, R.drawable.icon_four)
    private val five = BitmapFactory.decodeResource(resources, R.drawable.icon_five)
    private val six = BitmapFactory.decodeResource(resources, R.drawable.icon_six)
    private val seven = BitmapFactory.decodeResource(resources, R.drawable.icon_seven)
    private val eight = BitmapFactory.decodeResource(resources, R.drawable.icon_eight)
    private val nine = BitmapFactory.decodeResource(resources, R.drawable.icon_nine)

    private val numberList = ArrayList<Bitmap>()

    private val gameOver = BitmapFactory.decodeResource(resources, R.drawable.icon_game_over)
    private val scoreBoard = BitmapFactory.decodeResource(resources, R.drawable.other_record)


//    //数字图片 小
//    private val zero_small = BitmapFactory.decodeResource(resources, R.drawable.icon_zero_small)
//    private val one_small = BitmapFactory.decodeResource(resources, R.drawable.icon_one_small)
//    private val two_small = BitmapFactory.decodeResource(resources, R.drawable.icon_two_small)
//    private val three_small = BitmapFactory.decodeResource(resources, R.drawable.icon_three_small)
//    private val four_small = BitmapFactory.decodeResource(resources, R.drawable.icon_four_small)
//    private val five_small = BitmapFactory.decodeResource(resources, R.drawable.icon_five_small)
//    private val six_small = BitmapFactory.decodeResource(resources, R.drawable.icon_six_small)
//    private val seven_small = BitmapFactory.decodeResource(resources, R.drawable.icon_seven_small)
//    private val eight_small = BitmapFactory.decodeResource(resources, R.drawable.icon_eight_small)
//    private val nine_small = BitmapFactory.decodeResource(resources, R.drawable.icon_nine_small)


    //屏幕信息
    private val screenWidth = screenWidth()
    private val screenHeight = screenHeight()

    //游戏正在进行的模式
    private var gameMode = GameMode.MENU

    private val mHandler: Handler


    //使用set方法设置游戏模式，方便监听
    private fun setGameMode(gameMode: GameMode) {
        this.gameMode = gameMode
        _onModeChange?.invoke(gameMode)

        if (gameMode == GameMode.MENU || gameMode == GameMode.LOAD) {
            bm = if (random.nextInt(10) % 2 == 0) bm1 else bm2
            val i = random.nextInt(100) % 3
            mBird = Bird(if (i == 0) blueBirds else if (i == 1) redBirds else yellowBirds)
            mBird.earthHeight = bgBitmap.height
            mBird.dead {
                mHandler.sendEmptyMessage(RESULT)
            }
        }
        if (gameMode == GameMode.LOAD) {
            mBird.changeMode(Bird.PosMode.READY)
            mBird.readySize = loadCenterWidth / 2

            obs1.reload()
            obs2.reload()
            gameBg.reset()
            isDrawObs2 = false
            counter.reset()
        }

    }

    //底部按钮间距
    private val divide_size: Float
    //按钮按下时 出现偏移 形成按压效果
    private var btnOffset1 = 0
    private var btnOffset2 = 0

    private var isDrawObs2 = false
    private var counter = Counter()

    init {
//        setBackgroundResource(BirdApp.bg)
        isFocusable = true
        isFocusableInTouchMode = true
        //设置背景常亮
        keepScreenOn = true

        sfh = holder
        sfh.addCallback(this)

        mHandler = BirdHandler(this)
        //画笔初始化
        paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        //小鸟集合
        blueBirds.add(bird_blue_up)
        blueBirds.add(bird_blue_middle)
        blueBirds.add(bird_blue_down)

        redBirds.add(bird_red_up)
        redBirds.add(bird_red_middle)
        redBirds.add(bird_red_down)

        yellowBirds.add(bird_yellow_up)
        yellowBirds.add(bird_yellow_middle)
        yellowBirds.add(bird_yellow_down)
        //数字图片集合
        numberList.add(zero)
        numberList.add(one)
        numberList.add(two)
        numberList.add(three)
        numberList.add(four)
        numberList.add(five)
        numberList.add(six)
        numberList.add(seven)
        numberList.add(eight)
        numberList.add(nine)
        //小鸟的精灵类
        mBird = Bird(blueBirds)
        //菜单的标题绘制初始化
        val left = screenWidth / 8f
        val bottom = mBird.tempY - 65.toFloat()
        val menu_title_scale = menu_title.width.toFloat() / (screenWidth * 3 / 4f)

        val menu_title_height = menu_title.height / menu_title_scale

        menuTitleRectF.set(left, bottom - menu_title_height, 7 * left, bottom)

        divide_size = (screenWidth - startIcon.width * 2f) / 3f

        //移动背景的初始化
        bgBitmap = BitmapFactory.decodeResource(resources, R.drawable.background_ground)
        gameBg = GameBg(bgBitmap)
        rectF.set(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat())



        obs1 = Obstacle(downPillar, upPillar, bgBitmap.height)
        obs2 = Obstacle(downPillar, upPillar, bgBitmap.height)

        obs1.notice {
            isDrawObs2 = true
        }

        //绘制线程初始化
        thread = Thread(this)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        synchronized(this) {
            isStop = true
            mDrawFlag = false
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        synchronized(this) {
            if (mDrawFlag) {
                isStop = false
                bm = if (random.nextInt(10) % 2 == 0) bm1 else bm2
                val i = random.nextInt(100) % 3
                mBird = Bird(if (i == 0) blueBirds else if (i == 1) redBirds else yellowBirds)
                mBird.earthHeight = bgBitmap.height
                mBird.dead {
                    mHandler.sendEmptyMessage(RESULT)
                }
                thread.start()
            }
        }
    }

    override fun run() {
        while (true) {
            if (isStop)
                return
            logic()

            drawGame()
            SystemClock.sleep(5)
        }
    }

    //记录按下时的位置
    private var posFlag = -1

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //todo
        event ?: return super.onTouchEvent(event)

        when (gameMode) {
            GameMode.MENU -> {
                val x = event.x
                val y = event.y
                val corY = gameBg.bm1y - startIcon.height
                //判断按下的位置
                val flag: Int
                flag = if (x in divide_size..(divide_size + startIcon.width) && y in corY..(corY + startIcon.height)) {
                    START
                } else if (x in (divide_size * 2 + startIcon.width)..(divide_size * 2 + startIcon.width * 2) && y in corY..(corY + startIcon.height)) {
                    RANK
                } else {
                    NONE
                }


                //根据触摸操作 以及按下位置的标识，确定进一步的操作
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        when (flag) {
                            START -> {
                                btnOffset1 = 5
                            }
                            RANK -> {
                                btnOffset2 = 5
                            }
                            NONE -> {
                            }
                        }

                        posFlag = flag
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        //抬起手指 将偏移量改为0
                        btnOffset1 = 0
                        btnOffset2 = 0
                        //发送消息 进入加载阶段
                        when (posFlag) {
                            START -> {
                                //进入加载阶段
                                mHandler.sendEmptyMessage(LOAD)
                            }
                            RANK -> {
                                //进入排名
                            }
                            NONE -> {
                            }
                        }

                        return true
                    }
                    else -> return false
                }
            }

            GameMode.READY -> {
                mHandler.sendEmptyMessage(PLAYING)
                return true
            }

            GameMode.PLAYING -> {
                mBird.changeMode(Bird.PosMode.PRESS)

                return true
            }
            GameMode.RESULT -> {
                val x = event.x
                val y = event.y
                val corY = gameBg.bm1y - startIcon.height
                //判断按下的位置
                val flag: Int
                flag = if (x in divide_size..(divide_size + startIcon.width) && y in corY..(corY + startIcon.height)) {
                    START
                } else if (x in (divide_size * 2 + startIcon.width)..(divide_size * 2 + startIcon.width * 2) && y in corY..(corY + startIcon.height)) {
                    RANK
                } else {
                    NONE
                }


                //根据触摸操作 以及按下位置的标识，确定进一步的操作
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        when (flag) {
                            START -> {
                                btnOffset1 = 5
                            }
                            RANK -> {
                                btnOffset2 = 5
                            }
                            NONE -> {
                            }
                        }
                        posFlag = flag
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        //抬起手指 将偏移量改为0
                        btnOffset1 = 0
                        btnOffset2 = 0
                        //发送消息 进入加载阶段
                        when (posFlag) {
                            START -> {
                                //进入加载阶段
                                mHandler.sendEmptyMessage(LOAD)
                            }
                            RANK -> {
                                //进入排名
                            }
                            NONE -> {
                            }
                        }

                        return true
                    }
                    else -> return false
                }
            }
            else -> {

            }
        }

        return super.onTouchEvent(event)
    }

    //绘制游戏
    private fun drawGame() {
        try {
            canvas = sfh?.lockCanvas()
            //根据状态绘制不同的画面
            when (gameMode) {

                GameMode.MENU -> {
                    canvas?.drawBitmap(bm, null, rectF, paint)
                    canvas?.drawBitmap(menu_title, null, menuTitleRectF, paint)

                    val corY = gameBg.bm1y - startIcon.height * 1f
                    canvas?.drawBitmap(startIcon, divide_size, corY + btnOffset1, paint)
                    canvas?.drawBitmap(rankIcon, divide_size * 2f + startIcon.width, corY + btnOffset2, paint)

                    gameBg.draw(canvas!!, paint)
                    mBird.draw(canvas!!, paint)
                }
                GameMode.LOAD -> {
                    canvas!!.drawBitmap(loadBm, null, rectF, paint)
                    //加载状态1.5s后进入准备状态
                    postDelayed({
                        mHandler.sendEmptyMessage(READY)
                    }, 500)

                }
                GameMode.READY -> {
                    canvas!!.drawBitmap(bm, null, rectF, paint)

                    mBird.draw(canvas!!, paint)
                    gameBg.draw(canvas!!, paint)
                    val centerY = (screenHeight - loadCenterHeight) / 2
                    val centerX = (screenWidth - loadCenterWidth) / 2

                    canvas!!.drawBitmap(loadCenter, centerX.toFloat(), centerY.toFloat(), paint)
                    val readyX = (screenWidth - readyIcon.width) / 2

                    canvas!!.drawBitmap(readyIcon, readyX.toFloat(), centerY - 30f, paint)

                    val numX = (screenWidth - zero.width) / 2
                    canvas!!.drawBitmap(zero, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                }
                GameMode.PLAYING -> {

                    canvas!!.drawBitmap(bm, null, rectF, paint)

                    obs1.draw(canvas!!, paint)
                    if (isDrawObs2) {

                        obs2.draw(canvas!!, paint)
                    }

                    mBird.draw(canvas!!, paint)
                    gameBg.draw(canvas!!, paint)

                    val split = counter.split()
                    val centerY = (screenHeight - loadCenterHeight) / 2

                    when (split.size) {
                        1 -> {
                            val number = numberList[split[0]]
                            val numX = (screenWidth - number.width) / 2
                            canvas!!.drawBitmap(number, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                        }
                        2 -> {
                            val number1 = numberList[split[0]]
                            val number2 = numberList[split[1]]

                            val numX = (screenWidth - number1.width - number2.width - 10) / 2
                            canvas!!.drawBitmap(number1, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number2, numX + number1.width + 10f, centerY - 30f - readyIcon.height - 30f, paint)
                        }
                        3 -> {
                            val number1 = numberList[split[0]]
                            val number2 = numberList[split[1]]
                            val number3 = numberList[split[1]]

                            val numX = (screenWidth - number1.width - number2.width - 20 - number3.width) / 2
                            canvas!!.drawBitmap(number1, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number2, numX + number1.width + 10f, centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number3, numX + number1.width + number2.width + 20f, centerY - 30f - readyIcon.height - 30f, paint)
                        }
                    }


                }
                GameMode.RESULT, GameMode.GAME_OVER -> {
                    canvas!!.drawBitmap(bm, null, rectF, paint)


                    obs1.draw(canvas!!, paint)
                    if (isDrawObs2) {
                        obs2.draw(canvas!!, paint)
                    }

                    gameBg.draw(canvas!!, paint)

                    mBird.draw(canvas!!, paint)

                    if (gameMode==GameMode.RESULT){
                        val corY = gameBg.bm1y - startIcon.height * 1f
                        canvas?.drawBitmap(startIcon, divide_size, corY + btnOffset1, paint)
                        canvas?.drawBitmap(rankIcon, divide_size * 2f + startIcon.width, corY + btnOffset2, paint)

                        canvas?.drawBitmap(scoreBoard, (screenWidth - scoreBoard.width) / 2f, corY - 10f - scoreBoard.height, paint)
                    }

                    val split = counter.split()
                    val centerY = (screenHeight - loadCenterHeight) / 2

                    when (split.size) {
                        1 -> {
                            val number = numberList[split[0]]
                            val numX = (screenWidth - number.width) / 2
                            canvas!!.drawBitmap(number, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                        }
                        2 -> {
                            val number1 = numberList[split[0]]
                            val number2 = numberList[split[1]]

                            val numX = (screenWidth - number1.width - number2.width - 10) / 2
                            canvas!!.drawBitmap(number1, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number2, numX + number1.width + 10f, centerY - 30f - readyIcon.height - 30f, paint)
                        }
                        3 -> {
                            val number1 = numberList[split[0]]
                            val number2 = numberList[split[1]]
                            val number3 = numberList[split[1]]

                            val numX = (screenWidth - number1.width - number2.width - 20 - number3.width) / 2
                            canvas!!.drawBitmap(number1, numX.toFloat(), centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number2, numX + number1.width + 10f, centerY - 30f - readyIcon.height - 30f, paint)
                            canvas!!.drawBitmap(number3, numX + number1.width + number2.width + 20f, centerY - 30f - readyIcon.height - 30f, paint)
                        }
                    }

//                    postDelayed({
//                        if (gameMode == GameMode.GAME_OVER)
//
//                    }, 500)

                }
//                 -> {
//
//                }
            }

        } catch (e: Exception) {
            Log.e("TAG", e.message, e)
        } finally {
            if (null != sfh && null != canvas)
                sfh.unlockCanvasAndPost(canvas)
        }
    }

    //游戏的逻辑
    private fun logic() {


        when (gameMode) {
            GameMode.MENU -> {
                gameBg.logic()
                mBird.logic()
            }
            GameMode.LOAD -> {
                counter.reset()
            }
            GameMode.READY -> {
                gameBg.logic()
                mBird.logic()
            }
            GameMode.PLAYING -> {
                gameBg.logic()
                mBird.logic()

                obs1.logic()
                if (isDrawObs2) {
                    obs2.logic()
                }

                if (mBird.isCollisionWith(obs1) || mBird.isCollisionWith(obs2) || mBird.isCollisionWithEarth()) {
                    //碰撞
                    e("碰撞")
                    mHandler.sendEmptyMessage(GAME_OVER)
                    mBird.changeMode(Bird.PosMode.DEAD)
                } else if (mBird.isCross(obs1) || mBird.isCross(obs2)) {
                    //通过障碍物
                    e("通过")
                    counter++
                }

            }
            GameMode.GAME_OVER -> {
                mBird.logic()
                obs1.isDead = true
                obs2.isDead = true
                gameBg.isDead = true

            }
            GameMode.RESULT -> {

            }
        }

    }

    //停止游戏
    fun stop() {
        isStop = true
    }

    private var _onModeChange: ((gameMode: GameMode) -> Unit)? = null

    fun setOnModeChangeListener(listener: (gameMode: GameMode) -> Unit) {
        _onModeChange = listener
    }

    companion object {
        private val MENU = 0
        private val LOAD = 1
        private val READY = 2
        private val PLAYING = 3
        private val GAME_OVER = 4
        private val RESULT = 5

        private val START = 0
        private val RANK = 1
        private val NONE = -1

        class BirdHandler(birdView: BirdView) : Handler() {
            private var mPreference: WeakReference<BirdView>? = null

            init {
                mPreference = WeakReference(birdView)
            }

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                mPreference ?: return

                val birdView = mPreference!!.get() ?: return
                when (msg?.what) {
                    MENU -> {
                        birdView.setGameMode(GameMode.MENU)
                    }
                    LOAD -> {
                        birdView.setGameMode(GameMode.LOAD)
                    }
                    READY -> {
                        birdView.setGameMode(GameMode.READY)
                    }
                    PLAYING -> {
                        birdView.setGameMode(GameMode.PLAYING)
                    }
                    GAME_OVER -> {
                        birdView.setGameMode(GameMode.GAME_OVER)
                    }
                    RESULT -> {
                        birdView.setGameMode(GameMode.RESULT)
                    }
                }
            }
        }
    }
}