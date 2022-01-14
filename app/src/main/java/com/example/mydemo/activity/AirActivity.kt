package com.example.mydemo.activity

import android.os.Bundle
import com.example.mydemo.R
import com.example.mydemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_air.*

/**
 * 空调面板自定义view展示
 * */
class AirActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_air
    }

    override fun initView() {

        cp.setValue(25,30)
    }

    override fun addListener() {

    }

    override fun onResume() {
        super.onResume()
//        cp.setValue(28,30)
    }
}



