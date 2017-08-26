@file:Suppress("unused")

package hy.flappybird.utils

import android.os.Environment
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created time : 2017/8/3 10:07.
 * @author HY
 */
var isSaveLog = true // 是否把保存日志到SD卡中
val ROOT = Environment.getExternalStorageDirectory().path + "/zanq/" // SD卡中的根目录

val PATH_LOG_INFO = ROOT + "log/"

// 容许打印日志的类型，默认是true，设置为false则不打印
private val allowD = true
private val allowE = true
private val allowI = true
private val allowV = true
private val allowW = true
private val allowWtf = true

private fun generateTag(caller: StackTraceElement): String {
    val callerClassName = caller.className //get class name
    val clazzName = callerClassName.substring(callerClassName.lastIndexOf("") + 1)//get simple class name
    val methodName = caller.methodName
    val lineNum = caller.lineNumber

    val threadName = Thread.currentThread().name
    val shortName = if (threadName.startsWith("OkHttp")) "OkHttp" else threadName

    return "[$shortName]:$clazzName.$methodName[Line:$lineNum]"
}

private fun getCallerStackTraceElement(): StackTraceElement {
    return Thread.currentThread().stackTrace[4]
}

fun v(msg: Int?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Short?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Byte?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Long?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Char?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Boolean?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Float?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Double?, tr: Throwable? = null) = v(msg.toString(), tr)
fun v(msg: Any?, tr: Throwable? = null) = v(msg.toString(), tr)

fun v(msg: String?, tr: Throwable? = null) {
    if (!allowV) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.v(tag, msg.toString(), tr)
}

fun d(msg: Int?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Short?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Byte?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Long?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Char?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Boolean?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Float?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Double?, tr: Throwable? = null) = d(msg.toString(), tr)
fun d(msg: Any?, tr: Throwable? = null) = d(msg.toString(), tr)

fun d(msg: String?, tr: Throwable? = null) {
    if (!allowD) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.d(tag, msg.toString(), tr)
}

fun i(msg: Int?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Short?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Byte?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Long?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Char?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Boolean?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Float?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Double?, tr: Throwable? = null) = i(msg.toString(), tr)
fun i(msg: Any?, tr: Throwable? = null) = i(msg.toString(), tr)

fun i(msg: String?, tr: Throwable? = null) {
    if (!allowI) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.i(tag, msg.toString(), tr)
}

fun w(msg: Int?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Short?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Byte?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Long?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Char?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Boolean?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Float?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Double?, tr: Throwable? = null) = w(msg.toString(), tr)
fun w(msg: Any?, tr: Throwable? = null) = w(msg.toString(), tr)

fun w(msg: String?, tr: Throwable? = null) {
    if (!allowW) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.w(tag, msg.toString(), tr)
}

fun e(msg: Int?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Short?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Byte?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Long?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Char?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Boolean?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Float?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Double?, tr: Throwable? = null) = e(msg.toString(), tr)
fun e(msg: Any?, tr: Throwable? = null) = e(msg.toString(), tr)

fun e(msg: String?, tr: Throwable? = null) {
    if (!allowE) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.e(tag, msg.toString(), tr)

    if (isSaveLog) {
        if (null != tr) {

            point(PATH_LOG_INFO, tag, getStackTrace(tr))
        }
    }
}

fun wtf(msg: Int?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Short?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Byte?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Long?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Char?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Boolean?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Float?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Double?, tr: Throwable? = null) = wtf(msg.toString(), tr)
fun wtf(msg: Any?, tr: Throwable? = null) = wtf(msg.toString(), tr)

fun wtf(msg: String?, tr: Throwable? = null) {
    if (!allowWtf) return

    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.wtf(tag, msg.toString(), tr)
}


fun point(path: String, tag: String, msg: String) {
    var pathname = path
    if (isSDAva()) {
        val date = Date()
        val dateFormat = SimpleDateFormat("",
                Locale.SIMPLIFIED_CHINESE)
        dateFormat.applyPattern("yyyy")
        pathname = pathname + dateFormat.format(date) + "/"
        dateFormat.applyPattern("MM")
        pathname += dateFormat.format(date) + "/"
        dateFormat.applyPattern("dd")
        pathname += dateFormat.format(date) + ".log"
        dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]")
        val time = dateFormat.format(date)
        val file = File(pathname)
        if (!file.exists())
            createDipPath(pathname)
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(
                    FileOutputStream(file, true)))
            out.write("$time $tag $msg\r\n")
        } catch (e: Exception) {
            d(e.javaClass.simpleName, e)
        } finally {
            try {
                if (out != null) {
                    out.close()
                }
            } catch (e: IOException) {
                d(e.javaClass.simpleName, e)
            }

        }
    }
}

/**
 * 根据文件路径 递归创建文件
 * @param file
 */
fun createDipPath(file: String) {
    val parentFile = file.substring(0, file.lastIndexOf("/"))
    val file1 = File(file)
    val parent = File(parentFile)
    if (!file1.exists()) {
        parent.mkdirs()
        try {
            file1.createNewFile()
        } catch (e: IOException) {
            d(e.javaClass.simpleName, e)
        }

    }
}

/**
 * 完整的堆栈信息

 * @param e Exception
 * *
 * @return Full StackTrace
 */
private fun getStackTrace(e: Throwable): String {
    var sw: StringWriter? = null
    var pw: PrintWriter? = null
    try {
        sw = StringWriter()
        pw = PrintWriter(sw)
        e.printStackTrace(pw)
        pw.flush()
        sw.flush()
    } finally {
        if (sw != null) {
            try {
                sw.close()
            } catch (e1: IOException) {
                d(e1.javaClass.simpleName, e1)
            }

        }
        if (pw != null) {
            pw.close()
        }
    }
    return sw!!.toString()
}

/**
 * A little trick to reuse a formatter in the same thread
 */
private class ReusableFormatter {

    private val formatter: Formatter
    private val builder = StringBuilder()

    init {
        formatter = Formatter(builder)
    }

    fun format(msg: String, vararg args: Any): String {
        formatter.format(msg, *args)
        val s = builder.toString()
        builder.setLength(0)
        return s
    }
}

private val thread_local_formatter = object : ThreadLocal<ReusableFormatter>() {
    override fun initialValue(): ReusableFormatter {
        return ReusableFormatter()
    }
}

fun format(msg: String, vararg args: Any): String {
    val formatter = thread_local_formatter.get()
    return formatter.format(msg, *args)
}

fun isSDAva(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageDirectory().exists()
}