package com.smart.mylib2

import android.util.Log

/**
 *Date: 2022/1/14
 *author: hxc
 */
object TestUtils {

    private val classMap = HashMap<String,NameInterface>()

    fun getNameManager(a:String):NameInterface{

        if(classMap.containsKey(a)){
            return classMap[a] as NameInterface
        } else {
            val nameImpl = Class.forName("com.smart.mylib2.NameImpl")
            classMap[a] = nameImpl.newInstance() as NameInterface
            return classMap[a] as NameInterface
        }

//        val nameImpl = Class.forName("com.smart.mylib2.NameImpl")
//        return nameImpl.newInstance() as NameInterface
//        return NameImpl.getInstall()
    }

}