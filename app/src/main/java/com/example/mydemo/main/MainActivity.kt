package com.example.mydemo.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydemo.R
import com.example.mydemo.activity.*
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.countdown.CountDownTimerActivity
import com.example.mydemo.deviceInfo.DeviceActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.example.mydemo.anim.AnimActivity
import com.example.mydemo.coordinator_Layout.CoordinatorLayoutActivity
import com.example.mydemo.leftscroll.LeftScrollActivity
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.progress.ProgressActivity
import com.example.mydemo.wheel.WheelActivity
import com.smart.mylib2.TestUtils


class MainActivity : BaseActivity() {

    private val tabList = arrayListOf("滚轮效果","拖拽排序","左滑删除","仪表盘","进度条",
        "倒计时","动画","下载","联动","设备","根据色值判断点击事件","验证码","颜色选择","黑白化",
    "录音","测试")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val layoutManager = GridLayoutManager(this,2)
        rv_main.layoutManager = layoutManager
        if(rv_main.adapter == null){
            rv_main.adapter = MainAdapter(this,tabList){
                when(it){
                    "滚轮效果" -> {
                        //滚轮
                        var intent = Intent(this@MainActivity, WheelActivity::class.java)
                        startActivity(intent)
                    }
                    "拖拽排序" -> {
                        //拖拽排序
                        var intent = Intent(this@MainActivity, DragSortActivity::class.java)
                        startActivity(intent)
                    }
                    "左滑删除" -> {
                        //左滑删除
                        var intent = Intent(this@MainActivity, LeftScrollActivity::class.java)
                        startActivity(intent)
                    }
                    "仪表盘" -> {
                        //自定义仪表盘
                        var intent = Intent(this@MainActivity, AirActivity::class.java)
                        startActivity(intent)
                    }
                    "进度条" -> {
                        //进度条
                        var intent = Intent(this@MainActivity, ProgressActivity::class.java)
                        startActivity(intent)
                    }
                    "倒计时" -> {
                        //倒计时
                        var intent = Intent(this@MainActivity, CountDownTimerActivity::class.java)
                        startActivity(intent)
                    }
                    "动画" -> {
                        //动画
                        var intent = Intent(this@MainActivity, AnimActivity::class.java)
                        startActivity(intent)
                    }
                    "下载" -> {
                        //下载
                        var intent = Intent(this@MainActivity, DownLoadActivity::class.java)
                        startActivity(intent)
                    }
                    "联动" -> {
                        var intent = Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)
                        startActivity(intent)
                    }
                    "设备" -> {
                        //设备
                        var intent = Intent(this@MainActivity, DeviceActivity::class.java)
                        startActivity(intent)
                    }
                    "根据色值判断点击事件" -> {
                        var intent = Intent(this@MainActivity, PeopleActivity::class.java)
                        startActivity(intent)
                    }
                    "验证码" -> {
                        var intent = Intent(this@MainActivity, CodeActivity::class.java)
                        startActivity(intent)
                    }
                    "颜色选择" -> {
                        var intent = Intent(this@MainActivity, ColorPickerActivity::class.java)
                        startActivity(intent)
                    }
                    "黑白化" -> {
                        var intent = Intent(this@MainActivity, BlackWhiteActivity::class.java)
                        startActivity(intent)
                    }
                    "录音" -> {
                        var intent = Intent(this@MainActivity, MicActivity::class.java)
                        startActivity(intent)
                    }
                    "测试" -> {
                        var intent = Intent(this@MainActivity, TestActivity::class.java)
                        startActivity(intent)
                    }

                }
            }
        } else {
            rv_main.adapter!!.notifyDataSetChanged()
        }
    }

    override fun addListener() {
        TestUtils.getNameManager("name").setName("haha1")
        Utils.log(TAG, TestUtils.getNameManager("name").getName())

    }
}
