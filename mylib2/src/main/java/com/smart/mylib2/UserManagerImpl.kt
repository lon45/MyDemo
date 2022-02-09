package com.smart.mylib2

import androidx.annotation.IntRange

/**
 *Date: 2022/1/14
 *author: hxc
 */
class UserManagerImpl:UserManager {

    private var name = "XX"
    private var age = 0
    @IntRange(from = 0,to = 1)
    private var sex = 0

    override fun setName(mName:String) {
        name = mName
    }

    override fun getName(): String {
        return name
    }

    override fun setAge(mAge: Int) {
        age = mAge
    }

    override fun getAge(): Int {
        return age
    }

    override fun setSex(mSex: Int) {
        sex = mSex
    }

    override fun getSex(): Int {
        return sex
    }
}