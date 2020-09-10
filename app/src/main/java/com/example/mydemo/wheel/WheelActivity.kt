package com.example.mydemo.wheel

import android.os.Bundle
import com.example.mydemo.BaseActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_wheel.*

/**
 *Date: 2020/9/10
 *author: hxc
 * 滚轮
 */
class WheelActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wheel)

        val wheelList = ArrayList<String>()
        for(i in 0 until 10){
            wheelList.add("第$i 条")
        }

        wheel.data = wheelList
    }

}