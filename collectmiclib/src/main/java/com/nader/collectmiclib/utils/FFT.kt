package com.nader.collectmiclib.utils

import android.util.Log
import kotlin.math.cos
import kotlin.math.sin

/**
 *Date: 2021/5/13
 *author: hxc
 * 快速傅里叶变换
 */
object FFT {

    private fun fft(x: Array<Complex?>): Array<Complex?> {

        var n = x.size
// 因为exp(-2i*n*PI)=1，n=1时递归原点
        if (n == 1) {
            return x
        }
        // 如果信号数为奇数，使用dft计算
        if (n % 2 != 0) {
            return dft(x)!!
        }

        // 提取下标为偶数的原始信号值进行递归fft计算
        val even = arrayOfNulls<Complex>(n / 2)
        for (k in 0 until n / 2) {
            even[k] = x[2 * k]
        }
        val evenValue = fft(even)

        // 提取下标为奇数的原始信号值进行fft计算
        // 节约内存
        var odd: Array<Complex?> = even
        for (k in 0 until n / 2) {
            odd[k] = x[2 * k + 1]
        }

        var oddValue = fft(odd)

        // 偶数+奇数
        var result = arrayOfNulls<Complex>(n)
        for (k in 0 until n / 2) {
            // 使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
            val p: Double = -2 * k * Math.PI / n
            val m = Complex(cos(p), sin(p))
            result[k] = evenValue[k]!!.plus(m.multiple(oddValue[k]!!)!!)
            // exp(-2*(k+n/2)*PI/n) 相当于 -exp(-2*k*PI/n)，其中exp(-n*PI)=-1(欧拉公式);
            result[k + n / 2] = evenValue[k]!!.minus(m.multiple(oddValue[k]!!)!!)
        }
        return result
    }

    private fun dft(x: Array<Complex?>): Array<Complex?>? {
        val n = x.size

        // 1个信号exp(-2i*n*PI)=1
        if (n == 1) return x
        val result = arrayOfNulls<Complex>(n)
        for (i in 0 until n) {
            result[i] = Complex(0.0, 0.0)
            for (k in 0 until n) {
                //使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
                val p = -2 * k * Math.PI / n
                val m = Complex(cos(p), sin(p))
                result[i]!!.plus(x[k]!!.multiple(m)!!)
            }
        }
        return result
    }


    /**
     * 获取最大的频率
     *
     * @param data
     * @param SAMPLE_RATE
     * @param FFT_N
     * @return
     */
    fun getFrequency(data: ByteArray, SAMPLE_RATE: Int, FFT_N: Int): Double {
        if (data.size < FFT_N) {
            throw RuntimeException("Data length lower than $FFT_N")
        }
        var f = arrayOfNulls<Complex>(FFT_N)
        for (i in 0 until FFT_N) {
            f[i] = Complex(data[i].toDouble(), 0.0) //实部为正弦波FFT_N点采样，赋值为1
            //虚部为0
        }

        f = fft(f) //进行快速福利叶变换

        val s = DoubleArray(FFT_N / 2)
        for (i in 0 until FFT_N / 2) {
            s[i] = f[i]!!.getMod()
        }
        var fmax = 0
        for (i in 1 until FFT_N / 2) {  //利用FFT的对称性，只取前一半进行处理
            if (s[i] > s[fmax]) fmax = i //计算最大频率的序号值
        }
        var fre = fmax * SAMPLE_RATE.toDouble() / FFT_N
//        Log.i("FFT", "fre:$fre | $fmax | $SAMPLE_RATE | $FFT_N")
        //能量超过XX才返回值
        if(s[fmax] <7000){
//            Log.i("FFT", "fre:${s[fmax]}")
            fre = (-1).toDouble()
        } else {
        Log.i("FFT", "fre:${s[fmax]} | $fre")
        }
        return fre
    }

}