package com.example.mydemo.coordinator_Layout

import java.io.Serializable

/**
 *Date: 2020/4/20
 *author: hxc
 */
data class CoordBean (
    var name :String = "",
    var info :ArrayList<String> = arrayListOf()

) :Serializable