package hy.flappybird

import android.app.Application
import hy.flappybird.utils.screenHeight
import hy.flappybird.utils.screenWidth

/**
 * Created time : 2017/8/23 15:00.
 * @author HY
 */
class BirdApp : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: BirdApp? = null

        val screenHeight: Int
            get() = instance?.screenHeight()!!

        val screenWidth: Int
            get() = instance?.screenWidth()!!
    }
}

