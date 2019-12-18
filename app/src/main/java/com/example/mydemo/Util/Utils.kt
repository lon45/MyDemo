package com.example.mydemo.Util

/**
 * Created by Ys on 2017/7/24.
 */

object Utils {
    // 防暴力点击
    private var lastClickTime: Long = 0

    fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (0 < timeD && timeD < 550) {
            return false// 不可点击
        }
        lastClickTime = time
        return true// 可以点击
    }

    fun getUserId() : String{

        return "12345"

    }
}
