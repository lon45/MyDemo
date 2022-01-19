package com.example.mydemo.util

import android.content.Context
import android.util.Log

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

    fun log(tag:String,msg:String){
        Log.i("lxzn_$tag",msg)
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

    fun colorIntToRGB(color:Int):String{

        val red: Int = color and 0xff0000 shr 16
//        Log.i("colorIntToRGB","red${red}")
        val green: Int = color and 0x00ff00 shr 8
//        Log.i("colorIntToRGB","green${green}")
        val blue: Int = color and 0x0000ff
//        Log.i("colorIntToRGB","blue${blue}")
//        return "#$red$green$blue"

        val redStr = if(Integer.toHexString(red).length>1) "${Integer.toHexString(red)}" else  "0${Integer.toHexString(red)}"
        Log.i("colorIntToRGB","redStr${redStr}")
        val greenStr = if(Integer.toHexString(green).length>1) "${Integer.toHexString(green)}" else  "0${Integer.toHexString(green)}"
        Log.i("colorIntToRGB","greenStr${greenStr}")
        val blueStr = if(Integer.toHexString(blue).length>1) "${Integer.toHexString(blue)}" else  "0${Integer.toHexString(blue)}"
        Log.i("colorIntToRGB","blueStr${blueStr}")
        return "#${redStr}${greenStr}${blueStr}"
    }
}
