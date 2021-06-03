package com.nader.collectmiclib.utils

import android.util.Log
import kotlin.math.abs
import kotlin.math.log10

/**
 *Date: 2021/5/13
 *author: hxc
 */
object VoiceUtil {

    /**
     * 获取声音的分贝
     *
     * @param bufferRead
     * @param lenght
     * @return
     */
    fun getVolume(bufferRead: ByteArray, lenght: Int): Double {
        var volume = 0
        for (i in bufferRead.indices) {
            volume += bufferRead[i] * bufferRead[i]
        }
        val mean: Double = volume.toDouble() / lenght
        return 10 * log10(mean)//mean //
    }

    fun getVolume2(buffer : ByteArray):Int{

            var sumVolume = 0.0
            var avgVolume = 0.0
            var volume = 0.0
//        if(isFirst) {
//            isFirst = false
            var i = 0
            while (i < buffer.size) {
                var v1 = buffer[i].toInt() and 0xFF
                var v2 = buffer[i + 1].toInt() and 0xFF
//                Log.i("calculateVolume","${v1} | ${v2}")
                var temp = v1 + (v2 shl 8) // 小端
                if (temp < 0x8000) {
                    temp = 0xffff - temp
                }
                sumVolume += abs(temp)
                i += 2
            }
            avgVolume = sumVolume / buffer.size / 2
//            Log.i("calculateVolume","${avgVolume}")
            volume = log10(1 + avgVolume) * 10
            Log.i("calculateVolume","${volume}")
//        }

        return volume.toInt()

    }

    var isFirst = true
    fun calculateVolume(var0: ByteArray, var1: Int) {
        if(isFirst) {
            isFirst = false
            for (i in var0) {
                Log.i("calculateVolume", "$i")
            }
        }
    }

//    fun calculateVolume(var0: ByteArray, var1: Int): Int {
//        var var3: IntArray? = null
//        var var4 = var0.size
////        Log.i("calculateVolume","$var1 | $var4")
//        if (var1 == 8) {
//            var3 = IntArray(var4)
//            for(i in 0..var4){
//                var3[i] = var0[i].toInt()
//            }
//        } else if (var1 == 16) {
//            var3 = IntArray(var4 / 2)
//            for(i in 0..(var3.size)){
//                val var5 = var0[i*2].toInt()
//                val var6 = var0[i * 2 + 1].toInt()
////                Log.i("calculateVolume","${var5} | ${var6}")
//                var var13 = 0
//                if(var5 < 0){
//                    var13 = var5 + 256
//                } else {
//                    var13 = var5
//                }
//                val var7 = var13.toShort()
//
//                if(var6 < 0) {
//                    var13 = var6 + 256
//                } else {
//                    var13 = var6
//                }
//                var3[i] = (var7 + (var13 shl 8))
//                Log.i("calculateVolume","w${var3[i]}")
//            }
//        }
//        Log.i("calculateVolume","aaaa")
//        if(var3 != null && var3.isNotEmpty()){
//
//            var var10 = 0.0f
//            for(i in 0..(var3.size)){
//                var10 += (var3[i] * var3[i])
//            }
//            var10 /= var3.size
//            Log.i("calculateVolume","${var10}")
//            var var12 = 0.0f
//            for(i in 0..(var3.size)){
//                var12 += var3[i]
//            }
//            var12 /= var3.size
//            Log.i("calculateVolume","${var12}")
//            var4 = ((2.0.pow(((var1 - 1).toDouble())) - 1.0).toInt())
//            val var14 = sqrt((var10 - var12 * var12))
//            var var9 = (10.0 * log10(var14 * 10.0 * sqrt(2.0) / var4.toDouble() + 1.0)).toInt()
//            Log.i("calculateVolume","${var9}")
//            if(var9 < 0){
//                var9 = 0
//            }
//            if(var9 > 10) {
//                var9 = 10
//            }
//            Log.i("calculateVolume","${var9}")
//            return var9
//
//        } else {
//            return 0
//        }
//
//    }

}