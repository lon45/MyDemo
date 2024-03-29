package com.example.mydemo.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydemo.R
import com.example.mydemo.activity.*
import com.example.mydemo.anim.AnimActivity
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.coordinator_Layout.CoordinatorLayoutActivity
import com.example.mydemo.countdown.CountDownTimerActivity
import com.example.mydemo.deviceInfo.DeviceActivity
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.leftexit.LeftExitActivity
import com.example.mydemo.leftscroll.LeftScrollActivity
import com.example.mydemo.progress.ProgressActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.wheel.WheelActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader
import java.io.LineNumberReader


class MainActivity : BaseActivity() {

    private val tabList = arrayListOf(
        "滚轮效果", "拖拽排序", "左滑删除", "仪表盘", "进度条",
        "倒计时", "动画", "下载", "联动", "设备", "根据色值判断点击事件", "验证码", "颜色选择", "黑白化",
        "录音", "测试", "左滑退出"
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val layoutManager = GridLayoutManager(this, 2)
        rv_main.layoutManager = layoutManager
        if (rv_main.adapter == null) {
            rv_main.adapter = MainAdapter(this, tabList) {
                when (it) {
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
                        var intent =
                            Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)
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
                    "左滑退出" -> {
                        var intent = Intent(this@MainActivity, LeftExitActivity::class.java)
                        startActivity(intent)
                    }

                }
            }
        } else {
            rv_main.adapter!!.notifyDataSetChanged()
        }
    }

    override fun addListener() {
//        Utils.log("", ThirdPartyManager.getUserManager().getName())

//        Utils.log("lxzn", getMac())
//        Utils.log("lxzn", getProp())
//        Utils.log("lxzn_uuid", getUUid2())
//        Utils.log("lxzn_KEY", getKey())
    }

    fun getMac(): String {
        var mac = ""
        try {
//            val process =
//                Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val process =
                Runtime.getRuntime().exec("cat /sys/class/net/eth0/address")
            val ir = InputStreamReader(process.inputStream)
            val input = LineNumberReader(ir)
            mac = input.readLine()
            mac = mac?.trim { it <= ' ' } ?: ""
        } catch (e: Exception) {
        }
        return mac
    }

    //    private fun getProp(): String {
//        var prop = ""
//        try {
//            val process =
//                Runtime.getRuntime().exec("getprop ro.runtime.firstboot")
//            val ir = InputStreamReader(process.inputStream)
//            val input = LineNumberReader(ir)
//            prop = input.readLine()
//            prop = prop?.trim { it <= ' ' } ?: ""
//        } catch (e: Exception) {
//        }
//        return prop
//    }
    private fun getUUid(): String {
        var uuid = ""
        try {
            val process =
                Runtime.getRuntime().exec("getprop ro.tuya.uuid")
            val ir = InputStreamReader(process.inputStream)
            val input = LineNumberReader(ir)
            uuid = input.readLine()
            uuid = uuid?.trim { it <= ' ' } ?: ""
        } catch (e: Exception) {
        }
        return uuid
    }

    @SuppressLint("PrivateApi")
    private fun getUUid2(): String {
        var uuid = ""
        try {
            val clazz = Class.forName("android.os.SystemProperties")
//            val methodSet = clazz.getMethod("set", String::class.java, String::class.java)
//            methodSet.isAccessible = true
//            methodSet.invoke(null, "ro.tuya.uuid","lx22f391937ad5e158a5")

            val methodGet = clazz.getMethod("get", String::class.java)
            methodGet.isAccessible = true
            uuid = methodGet.invoke(null, "ro.tuya.uuid") as String
        } catch (e: Exception) {
        }
        return uuid
    }

    @SuppressLint("PrivateApi")
    private fun getKey(): String {
        var key = ""
        try {
            val clazz = Class.forName("android.os.SystemProperties")
//            val methodSet = clazz.getMethod("set", String::class.java, String::class.java)
//            methodSet.isAccessible = true
//            methodSet.invoke(null, "ro.tuya.key","3MSR8JYi5FbyULWS2irPYAJyhGZ8h3j6")

            val methodGet = clazz.getMethod("get", String::class.java)
            methodGet.isAccessible = true
            key = methodGet.invoke(null, "ro.tuya.key") as String
        } catch (e: Exception) {
        }
        return key
    }

    fun getPorp(propName: String): String {
        var propStr = ""
        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val methodGet = clazz.getMethod("get", String::class.java)
            methodGet.isAccessible = true
            propStr = methodGet.invoke(null, "ro.tuya.uuid") as String
        } catch (e: Exception) {
        }
        return propStr
    }
}
