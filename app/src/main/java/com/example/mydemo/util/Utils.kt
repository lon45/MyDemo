package com.example.mydemo.util

import android.content.Context

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

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    fun dpToPx(context: Context, dp: Float): Int {
        // 密度比dpi
        val scale = context.resources.displayMetrics.density
        // 140dp转成px动态加载
        return (scale * dp + 0.5f).toInt()
    }
}
