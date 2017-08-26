package hy.flappybird.utils

import java.lang.Exception
import java.util.*

/**
 * Created time : 2017/8/26 8:32.
 * 计数器类 防作弊用
 * 其实就是蛋疼没事干弄得
 * @author HY
 */
class Counter {

    private val random = Random()
    private var incremental = 0

    private var count = 0

    fun reset() {
        incremental = random.nextInt(10) + 10
        count = 0
    }

    operator fun inc(): Counter {
        this.count += incremental
        return this
    }

    fun getResult(): Int {
        return try {
            count / incremental
        } catch (e: Exception) {
            d(e.javaClass.simpleName, e)
            0
        }
    }



    fun split(): IntArray {
        val elements = getResult()
        if (elements < 10) {
            return intArrayOf(elements)
        } else if (elements < 100) {
            return intArrayOf(elements / 10, elements % 10)
        } else if (elements < 1000) {
            return intArrayOf(elements / 100, elements % 100 / 10, elements % 10)
        } else return intArrayOf(0)
    }

}