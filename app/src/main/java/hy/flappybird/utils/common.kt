package hy.flappybird.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import org.jetbrains.anko.displayMetrics
import java.lang.Exception

/**
 * Created time : 2017/8/16 11:27.
 * @author HY
 */
fun isEmpty(str: String?) = null == str || str.isEmpty()

fun parse(numStr: String?): Int {
    if (isEmpty(numStr)) {
        return 0
    }
    try {
        return java.lang.Integer.parseInt(numStr)
    } catch (e: Exception) {
        d(e.javaClass.simpleName, e)
        return 0
    }
}


/**
 * Created time : 2017/7/24 18:09.
 * @author HY
 */
private val EXTRA_KEY_STRING = "bundle"

@JvmOverloads
fun Fragment.toActivity(cla: Class<out Activity>, bundle: Bundle? = null, data: Uri? = null) = context.toActivity(cla, bundle, data)


/**
 * skip to other activity and take extra data and uri data

 * @param cla    class object  where you want skip
 * *
 * @param bundle extra bundle data
 * *
 * @param data   uri data
 */
@JvmOverloads
fun Context.toActivity(cla: Class<out Activity>, bundle: Bundle? = null, data: Uri? = null) {
    val intent = Intent(this, cla)
    if (null != bundle) intent.putExtra(EXTRA_KEY_STRING, bundle)
    if (null != data) intent.data = data
    startActivity(intent)
}

fun Fragment.toNewActivity(cla: Class<out Activity>) = context.toNewActivity(cla)

fun Context.toNewActivity(cla: Class<out Activity>) {
    val intent = Intent(this, cla)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}

fun Activity.getBundle(): Bundle? = intent.getBundleExtra(EXTRA_KEY_STRING)


fun Context.screenWidth() = displayMetrics.widthPixels
fun Context.screenHeight() = displayMetrics.heightPixels
fun Context.densityDpi() = displayMetrics.densityDpi

fun Fragment.screenWidth() = context.screenWidth()
fun Fragment.screenHeight() = context.screenHeight()
fun Fragment.densityDpi() = context.densityDpi()

fun View.screenWidth() = context.screenWidth()
fun View.screenHeight() = context.screenHeight()
fun View.densityDpi() = context.densityDpi()

inline fun <reified T : View> Dialog.find(@IdRes id: Int): T = findViewById(id)


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show(@AnimRes anim: Int) {
    show()
    animation = AnimationUtils.loadAnimation(context, anim)
}

fun View.hideAnim() {
    clearAnimation()
    hide()
}