package com.example.mydemo.wheel

import android.os.Bundle
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_wheel.*

/**
 *Date: 2020/9/10
 *author: hxc
 * 滚轮
 */
class WheelActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_wheel
    }

    override fun initView() {

        val wheelList = ArrayList<String>()
        for(i in 0 until 10){
            wheelList.add("第$i 条")
        }

        wheel.data = wheelList
    }

    override fun addListener() {

    }

}