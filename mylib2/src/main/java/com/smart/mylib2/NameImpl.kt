package com.smart.mylib2

import android.util.Log

/**
 *Date: 2022/1/14
 *author: hxc
 */
class NameImpl:NameInterface {

    companion object {
        private var install:NameInterface? = null
        fun getInstall():NameInterface{
            if(install == null){
                install = NameImpl()
            }
            return  install!!
        }
    }

    private var name = ""

    override fun setName(mName:String) {
        name = mName
    }

    override fun getName(): String {
        return name
    }
}