package hy.flappybird

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    private val birdView1: BirdView
        get() = BirdView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val birdView = birdView1
        setContentView(birdView)

//        mIv.setImageResource(getDrawable("intro_night_up"))

//        val sprite = RRead.getSpriteInfo(this)
//
//        val json = Gson().toJson(sprite)
//
//        Log.e("JSON=========", json)

//        val assets = assets
//        assets.open()
    }

    override fun onStop() {
        super.onStop()
        birdView1.stop()
    }

//    fun getDrawable(name: String): Int {
//        //你在图片文件前加一个字母，我这里用 a_
//        try {
//            val clazz = Class.forName("$packageName.R")
//            val classes = clazz.classes
//            for (cla in classes) {
//                if (cla.simpleName == "drawable") {
//                    return cla.getField(name).getInt(cla)
//                }
//            }
//
//            return 0
//        } catch (e: Exception) {
//            Log.e("Error   ", "", e)
//            return 0
//        }
//    }
}
