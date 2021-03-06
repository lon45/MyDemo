package com.nader.collectmiclib.utils

import android.graphics.Color
import android.util.Half
import androidx.annotation.ColorLong
import androidx.annotation.HalfFloat
import kotlin.experimental.and

/**
 *Date: 2021/5/13
 *author: hxc
 * Color 工具类
 */
object ColorUtils {
    /**
     * 赤橙黄绿青蓝紫 七原色
     *
     * 0xff0000, 0xFF7D00, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF, 0xFF0000
     */
    val COLOR_LIST = arrayListOf(0xff0000, 0xFF7D00, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF, 0xFF0000)

    /**
     * 渐变色70种
     *
     *0xffff0000, 0xffff0c00, 0xffff1900, 0xffff2500, 0xffff3200, 0xffff3e00, 0xffff4b00, 0xffff5700, 0xffff6400, 0xffff7000,
    0xffff7d00, 0xffff8a00, 0xffff9700, 0xffffa400, 0xffffb100, 0xffffbe00, 0xffffcb00, 0xffffd800, 0xffffe500, 0xfffff200,
    0xffffff00, 0xffe5ff00, 0xffccff00, 0xffb2ff00, 0xff99ff00, 0xff7fff00, 0xff66ff00, 0xff4cff00, 0xff33ff00, 0xff19ff00,
    0xff00ff00, 0xff00ff19, 0xff00ff33, 0xff00ff4c, 0xff00ff66, 0xff00ff7f, 0xff00ff99, 0xff00ffb2, 0xff00ffcc, 0xff00ffe5,
    0xff00ffff, 0xff00e5ff, 0xff00ccff, 0xff00b2ff, 0xff0099ff, 0xff007fff, 0xff0066ff, 0xff004cff, 0xff0033ff, 0xff0019ff,
    0xff0000ff, 0xff1900ff, 0xff3300ff, 0xff4c00ff, 0xff6600ff, 0xff7f00ff, 0xff9900ff, 0xffb200ff, 0xffcc00ff, 0xffe500ff,
    0xffff00ff, 0xffff00e5, 0xffff00cc, 0xffff00b2, 0xffff0099, 0xffff007f, 0xffff0066, 0xffff004c, 0xffff0033, 0xffff0019
     *
     */
    val COLOR_LIST_70 = arrayListOf(0xffff0000, 0xffff0c00, 0xffff1900, 0xffff2500, 0xffff3200, 0xffff3e00, 0xffff4b00, 0xffff5700, 0xffff6400, 0xffff7000,
        0xffff7d00, 0xffff8a00, 0xffff9700, 0xffffa400, 0xffffb100, 0xffffbe00, 0xffffcb00, 0xffffd800, 0xffffe500, 0xfffff200,
        0xffffff00, 0xffe5ff00, 0xffccff00, 0xffb2ff00, 0xff99ff00, 0xff7fff00, 0xff66ff00, 0xff4cff00, 0xff33ff00, 0xff19ff00,
        0xff00ff00, 0xff00ff19, 0xff00ff33, 0xff00ff4c, 0xff00ff66, 0xff00ff7f, 0xff00ff99, 0xff00ffb2, 0xff00ffcc, 0xff00ffe5,
        0xff00ffff, 0xff00e5ff, 0xff00ccff, 0xff00b2ff, 0xff0099ff, 0xff007fff, 0xff0066ff, 0xff004cff, 0xff0033ff, 0xff0019ff,
        0xff0000ff, 0xff1900ff, 0xff3300ff, 0xff4c00ff, 0xff6600ff, 0xff7f00ff, 0xff9900ff, 0xffb200ff, 0xffcc00ff, 0xffe500ff,
        0xffff00ff, 0xffff00e5, 0xffff00cc, 0xffff00b2, 0xffff0099, 0xffff007f, 0xffff0066, 0xffff004c, 0xffff0033, 0xffff0019)
    /**
     * 渐变色140种
     *
     * 0xffff0000, 0xffff0600, 0xffff0c00, 0xffff1200, 0xffff1900, 0xffff1f00, 0xffff2500, 0xffff2b00, 0xffff3200, 0xffff3800, 0xffff3e00, 0xffff4400, 0xffff4b00, 0xffff5100, 0xffff5700, 0xffff5d00, 0xffff6400, 0xffff6a00, 0xffff7000, 0xffff7600,
    0xffff7d00, 0xffff8300, 0xffff8a00, 0xffff9000, 0xffff9700, 0xffff9d00, 0xffffa400, 0xffffaa00, 0xffffb100, 0xffffb700, 0xffffbe00, 0xffffc400, 0xffffcb00, 0xffffd100, 0xffffd800, 0xffffde00, 0xffffe500, 0xffffeb00, 0xfffff200, 0xfffff800,
    0xffffff00, 0xfff2ff00, 0xffe5ff00, 0xffd8ff00, 0xffccff00, 0xffbfff00, 0xffb2ff00, 0xffa5ff00, 0xff99ff00, 0xff8cff00, 0xff7fff00, 0xff72ff00, 0xff66ff00, 0xff59ff00, 0xff4cff00, 0xff3fff00, 0xff33ff00, 0xff26ff00, 0xff19ff00, 0xff0cff00,
    0xff00ff00, 0xff00ff0c, 0xff00ff19, 0xff00ff26, 0xff00ff33, 0xff00ff3f, 0xff00ff4c, 0xff00ff59, 0xff00ff66, 0xff00ff72, 0xff00ff7f, 0xff00ff8c, 0xff00ff99, 0xff00ffa5, 0xff00ffb2, 0xff00ffbf, 0xff00ffcc, 0xff00ffd8, 0xff00ffe5, 0xff00fff2,
    0xff00ffff, 0xff00f2ff, 0xff00e5ff, 0xff00d8ff, 0xff00ccff, 0xff00bfff, 0xff00b2ff, 0xff00a5ff, 0xff0099ff, 0xff008cff, 0xff007fff, 0xff0072ff, 0xff0066ff, 0xff0059ff, 0xff004cff, 0xff003fff, 0xff0033ff, 0xff0026ff, 0xff0019ff, 0xff000cff,
    0xff0000ff, 0xff0c00ff, 0xff1900ff, 0xff2600ff, 0xff3300ff, 0xff3f00ff, 0xff4c00ff, 0xff5900ff, 0xff6600ff, 0xff7200ff, 0xff7f00ff, 0xff8c00ff, 0xff9900ff, 0xffa500ff, 0xffb200ff, 0xffbf00ff, 0xffcc00ff, 0xffd800ff, 0xffe500ff, 0xfff200ff,
    0xffff00ff, 0xffff00f2, 0xffff00e5, 0xffff00d8, 0xffff00cc, 0xffff00bf, 0xffff00b2, 0xffff00a5, 0xffff0099, 0xffff008c, 0xffff007f, 0xffff0072, 0xffff0066, 0xffff0059, 0xffff004c, 0xffff003f, 0xffff0033, 0xffff0026, 0xffff0019, 0xffff000c
     *
     */
    val COLOR_LIST_140 = arrayListOf(0xff0000, 0xff0600, 0xff0c00, 0xff1200, 0xff1900, 0xff1f00, 0xff2500, 0xff2b00, 0xff3200, 0xff3800, 0xff3e00, 0xff4400, 0xff4b00, 0xff5100, 0xff5700, 0xff5d00, 0xff6400, 0xff6a00, 0xff7000, 0xff7600,
        0xff7d00, 0xff8300, 0xff8a00, 0xff9000, 0xff9700, 0xff9d00, 0xffa400, 0xffaa00, 0xffb100, 0xffb700, 0xffbe00, 0xffc400, 0xffcb00, 0xffd100, 0xffd800, 0xffde00, 0xffe500, 0xffeb00, 0xfff200, 0xfff800,
        0xffff00, 0xf2ff00, 0xe5ff00, 0xd8ff00, 0xccff00, 0xbfff00, 0xb2ff00, 0xa5ff00, 0x99ff00, 0x8cff00, 0x7fff00, 0x72ff00, 0x66ff00, 0x59ff00, 0x4cff00, 0x3fff00, 0x33ff00, 0x26ff00, 0x19ff00, 0x0cff00,
        0x00ff00, 0x00ff0c, 0x00ff19, 0x00ff26, 0x00ff33, 0x00ff3f, 0x00ff4c, 0x00ff59, 0x00ff66, 0x00ff72, 0x00ff7f, 0x00ff8c, 0x00ff99, 0x00ffa5, 0x00ffb2, 0x00ffbf, 0x00ffcc, 0x00ffd8, 0x00ffe5, 0x00fff2,
        0x00ffff, 0x00f2ff, 0x00e5ff, 0x00d8ff, 0x00ccff, 0x00bfff, 0x00b2ff, 0x00a5ff, 0x0099ff, 0x008cff, 0x007fff, 0x0072ff, 0x0066ff, 0x0059ff, 0x004cff, 0x003fff, 0x0033ff, 0x0026ff, 0x0019ff, 0x000cff,
        0x0000ff, 0x0c00ff, 0x1900ff, 0x2600ff, 0x3300ff, 0x3f00ff, 0x4c00ff, 0x5900ff, 0x6600ff, 0x7200ff, 0x7f00ff, 0x8c00ff, 0x9900ff, 0xa500ff, 0xb200ff, 0xbf00ff, 0xcc00ff, 0xd800ff, 0xe500ff, 0xf200ff,
        0xff00ff, 0xff00f2, 0xff00e5, 0xff00d8, 0xff00cc, 0xff00bf, 0xff00b2, 0xff00a5, 0xff0099, 0xff008c, 0xff007f, 0xff0072, 0xff0066, 0xff0059, 0xff004c, 0xff003f, 0xff0033, 0xff0026, 0xff0019, 0xff000c)


    /**
     * 获取渐变过程中特定点的颜色
     *
     * @param startColor 开始的颜色
     * @param endColor   结束的颜色
     * @param radio      特定比率
     * @return 返回特定点的颜色
     */
    fun getColor(startColor: Int, endColor: Int, radio: Float): Int {
        val redStart = Color.red(startColor)
        val blueStart = Color.blue(startColor)
        val greenStart = Color.green(startColor)
        val redEnd = Color.red(endColor)
        val blueEnd = Color.blue(endColor)
        val greenEnd = Color.green(endColor)
        val red = (redStart + (redEnd - redStart) * radio).toInt()
        val greed = (greenStart + (greenEnd - greenStart) * radio).toInt()
        val blue = (blueStart + (blueEnd - blueStart) * radio).toInt()
        return Color.rgb(red, greed, blue)
    }

}