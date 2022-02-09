package com.smart.mylib2

/**
 *Date: 2022/2/9
 *author: hxc
 */
object ThirdPartyManager {

    fun getUserManager():UserManager{
        return ThirdPartyUtils.getManager(UserManager::class.java)
    }



}