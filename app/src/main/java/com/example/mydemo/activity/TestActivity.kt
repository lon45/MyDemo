package com.example.mydemo.activity

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.mydemo.R
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.views.GuideView
import kotlinx.android.synthetic.main.activity_test.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile

/**
 *Date: 2020/4/8
 *author: hxc
 */
class TestActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {

        tv_content.text =
            "占位字符占位字符占位字符，占位字符占位字符占位字符。占位字符占位字符占位字符占位字符占位字符占位字符，占位字符占位字符占位字符占位字符占位字符占位字符占位字。符占位字符占位字符占位字符占，位字符占位字符占位字，符占位字符占，位字符占位字符占位字符占位字符占位字符占位字符占。位字符占位字符，占位字符占位位字符占位字符占位字符占位字。符占位字符占位字符占位字符，占位字符占位字符，占位字符占位字符。占位字符占位字符占位字符，占位字符占位字符占位字符。占位字符占位字符占位字符占位字符占位字符占位字符，占位字符占位字符占位字符占位字符占位字符占位字符占位字。符占位字符占位字符占位字符占，位字符占位字符占位字，符占位字符占，位字符占位字符占位字符占位字符占位字符占位字符占。位字符占位字符，占位字符占位位字符占位字符占位字符占位字。符占位字符占位字符占位字符，占位字符占位字符，占位字符占位字符。占位字符占位字符占位字符，占位字符占位字符占位字符。占位字符占位字符占位字符占位字符占位字符占位字符，占位字符占位字符占位字符占位字符占位字符占位字符占位字。符占位字符占位字符占位字符占，位字符占位字符占位字，符占位字符占，位字符占位字符占位字符占位字符占位字符占位字符占。位字符占位字符，占位字符占位位字符占位字符占位字符占位字。符占位字符占位字符占位字符，占位字符占位字符，占位字符占位字符。占位字符占位字符占位字符，占位字符占位字符占位字符。占位字符占位字符占位字符占位字符占位字符占位字符，占位字符占位字符占位字符占位字符占位字符占位字符占位字。符占位字符占位字符占位字符占，位字符占位字符占位字，符占位字符占，位字符占位字符占位字符占位字符占位字符占位字符占。位字符占位字符，占位字符占位位字符占位字符占位字符占位字。符占位字符占位字符占位字符，占位字符占位字符，占位字符占位字符。"
//        tv_content.text =
//            "占位字符占位字占位字符，占位字位字符占位字符，占位字符占位字符占位字符占位字符占位字符占位字符占位字。符占位字符占位字符占位字符占，位字符占位字符占位字，符占位字符占，位字符占位字符占位字符占位字符占位字符占位字符占。位字符占位字符，占位字符占位位字符占位字符占位字符占位字。符占位字符占位字符占位字符，占位字符占位字符，占位字符占位字符。"


    }

    private var open485 = true

    fun writeGpio(gpio: String, value: Boolean) {
        var file: RandomAccessFile? = null
        try {
            file = RandomAccessFile(gpio, "rw")
            file.writeBytes(if (value) "1" else "0")
            file.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun addListener() {

        tv_485.setOnClickListener {
            open485 = !open485
            writeGpio("/sys/class/gpio/gpio115/value",open485)
            if(open485){
                tv_485.text = "开"
            } else {
                tv_485.text = "关"
            }
        }

        tv_open.setOnClickListener {

            var lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            rl_case.layoutParams = lp
            tv_open.visibility = View.GONE
            ll_fg.visibility = View.GONE
        }

        mGuideView.addGuideListener(object : GuideView.GuideListener {
            override fun onDirection(direction: GuideView.Direction) {
                Log.i("111111111111", "$direction")
            }

        })
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i("onWindowFocusChanged", "${ll_content.height}")
        Log.i("onWindowFocusChanged", "${rl_case.height}")

        if(ll_content.height < rl_case.height){// 初始化进来如果内容比较少，则不显示 tv_open
            var lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            rl_case.layoutParams = lp

            tv_open.visibility = View.GONE
            ll_fg.visibility = View.GONE
        } else {
            tv_open.visibility = View.VISIBLE
            ll_fg.visibility = View.VISIBLE
        }

    }

}