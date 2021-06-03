package com.nader.collectmiclib.utils

import kotlin.math.sqrt

/**
 *Date: 2021/5/13
 *author: hxc
 * 复数
 */
class Complex {

    /**
     * 实部
     */
    private var real = 0.0

    /**
     * 虚部
     */
    private var image = 0.0

    constructor():this(0.0, 0.0)

    constructor(real: Double, image: Double){
        this.real = real
        this.image = image
    }

    fun getReal(): Double {
        return real
    }

    fun getImage(): Double {
        return image
    }


    override fun toString(): String {
        return  "Complex{ real=$real, image=$image}"
    }

    /**
     * 加法
     *
     * @param other
     * @return
     */
    operator fun plus(other: Complex): Complex? {
        return Complex(getReal() + other.getReal(), getImage() + other.getImage())
    }

    /**
     * 乘法
     *
     * @param other
     * @return
     */
    fun multiple(other: Complex): Complex? {
        return Complex(
            getReal() * other.getReal() - getImage() * other.getImage(),
            getReal() * other.getImage() + getImage() * other.getReal()
        )
    }

    /**
     * 减法
     *
     * @param other
     * @return
     */
    operator fun minus(other: Complex): Complex? {
        return Complex(getReal() - other.getReal(), getImage() - other.getImage())
    }

    /**
     * 求模值
     *
     * @return
     */
    fun getMod(): Double {
        return sqrt(getReal() * getReal() + getImage() * getImage())
    }

}