package com.smart.mylib2

import androidx.annotation.IntRange

/**
 *Date: 2022/1/14
 *author: hxc
 */
interface UserManager {

    fun setName(name:String)

    fun getName():String

    fun setAge(age :Int)

    fun getAge():Int

    fun setSex(@IntRange(from = 0,to = 1)sex :Int)

    fun getSex():Int


}