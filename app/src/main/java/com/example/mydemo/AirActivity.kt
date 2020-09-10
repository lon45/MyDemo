package com.example.mydemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_air.*

/**
 * 空调面板自定义view展示
 * */
class AirActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air)

        cp.setValue(25,30)
    }

    override fun onResume() {
        super.onResume()
//        cp.setValue(28,30)
    }
}



