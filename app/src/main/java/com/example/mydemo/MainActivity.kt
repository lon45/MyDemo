package com.example.mydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.mydemo.DownLoad.DownLoadActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_hello.setOnClickListener{
//            val num = et.text.toString().toInt()

//            tv_hello.text = getPriceStr(et.text.toString().toInt())

            //倒计时
//            var intent = Intent(this@MainActivity, CountDownTimerActivity::class.java)
//            startActivity(intent)
            //下载
            var intent = Intent(this@MainActivity, DownLoadActivity::class.java)
            startActivity(intent)



        }





    }

    fun getPriceStr(price:Int) : String{//分
        var priceStr = ""
        if((price % 100) == 0){//整元
            priceStr = "${price / 100}"
        } else {//有角分
            priceStr = String.format("%1.2f", price.toFloat() / 100)

        }
        Log.i("111",priceStr)
        return priceStr
    }
}
