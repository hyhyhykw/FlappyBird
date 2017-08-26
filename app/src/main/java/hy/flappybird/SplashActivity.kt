package hy.flappybird

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import hy.flappybird.utils.toActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        toActivity(MainActivity::class.java)
        finish()
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation = AnimationUtils.loadAnimation(this, R.anim.image_alpha_anim)
        animation.setAnimationListener(this)
        mIvLoad.startAnimation(animation)
    }
}
