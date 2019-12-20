package com.example.mydemo

import android.content.Intent
import android.os.Bundle
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.countdown.CountDownTimerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_countdown.setOnClickListener {
            if(!Utils.isFastDoubleClick()){
                return@setOnClickListener
            }
            //倒计时
            var intent = Intent(this@MainActivity, CountDownTimerActivity::class.java)
            startActivity(intent)

        }

        tv_download.setOnClickListener{
            if(!Utils.isFastDoubleClick()){
                return@setOnClickListener
            }
            //下载
            var intent = Intent(this@MainActivity, DownLoadActivity::class.java)
            startActivity(intent)
        }


    }


}
