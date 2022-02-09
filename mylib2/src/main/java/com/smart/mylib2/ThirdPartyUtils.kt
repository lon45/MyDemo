package com.smart.mylib2

/**
 *Date: 2022/1/14
 *author: hxc
 */
object ThirdPartyUtils {

    private val maps = HashMap<String,String>()
    private val classMap = HashMap<String,UserManager>()

    init {
        val var0 = maps.apply {
            this["com.smart.mylib2.UserManager"] = "com.smart.mylib2.UserManagerImpl"
        }.iterator()

        while(var0.hasNext()){
             var var1 = var0.next()
            classMap[var1.key] = Class.forName(var1.value).newInstance() as UserManager
        }
    }

    fun <T> getManager(clss:Class<T>):T{
        val className = clss.name

        if(classMap.containsKey(className)){
            return classMap[className] as T
        } else {
            val nameImpl = Class.forName(className)
            classMap[className] = nameImpl.newInstance() as UserManager
            return classMap[className] as T
        }
    }

}