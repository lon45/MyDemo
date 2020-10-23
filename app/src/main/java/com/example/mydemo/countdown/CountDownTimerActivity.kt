package com.example.mydemo.countdown

import android.os.Bundle
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_count_down_timer.*

/**
 *Date: 2019/11/14
 *author: hxc
 * 倒计时
 */
class CountDownTimerActivity : BaseActivity() {

    private val timeList = arrayListOf(30,40,50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down_timer)


        lv.adapter = CountDownAdapter(this, timeList)


//        myCountTimer.setTime(20)

    }



}