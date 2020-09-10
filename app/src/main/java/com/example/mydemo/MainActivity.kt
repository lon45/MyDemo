package com.example.mydemo

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.viewpager.widget.ViewPager
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.countdown.CountDownTimerActivity
import com.example.mydemo.deviceInfo.DeviceActivity
import com.example.mydemo.interfaces.OnCallback
import com.example.mydemo.views.rollingtextview.CharOrder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_indicator.view.*
import java.lang.ref.WeakReference
import androidx.core.content.res.ResourcesCompat
import com.example.mydemo.anim.AnimActivity
import com.example.mydemo.coordinator_Layout.CoordinatorLayoutActivity
import com.example.mydemo.leftscroll.LeftScrollActivity
import com.example.mydemo.progress.ProgressActivity
import com.example.mydemo.wheel.WheelActivity


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_wheel.setOnClickListener {
            //滚轮
            var intent = Intent(this@MainActivity, WheelActivity::class.java)
            startActivity(intent)

        }

        tv_left_scroll.setOnClickListener {

            //左滑删除
            var intent = Intent(this@MainActivity, LeftScrollActivity::class.java)
            startActivity(intent)
        }

        tv_pan.setOnClickListener {

            //自定义仪表盘
            var intent = Intent(this@MainActivity, AirActivity::class.java)
            startActivity(intent)
        }

        tv_progress.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //进度条
            var intent = Intent(this@MainActivity, ProgressActivity::class.java)
            startActivity(intent)

        }

        tv_countdown.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //倒计时
            var intent = Intent(this@MainActivity, CountDownTimerActivity::class.java)
            startActivity(intent)

        }

        tv_anim.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //动画
            var intent = Intent(this@MainActivity, AnimActivity::class.java)
            startActivity(intent)

        }


        tv_download.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //下载
            var intent = Intent(this@MainActivity, DownLoadActivity::class.java)
            startActivity(intent)
        }

        tv_liandong.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //联动
            var intent = Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)
            startActivity(intent)
//            var intent = Intent(this@MainActivity, TestActivity::class.java)
//            startActivity(intent)

        }

        tv_device.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //设备
            var intent = Intent(this@MainActivity, DeviceActivity::class.java)
            startActivity(intent)

        }
        //根据色值判断点击事件
        tv_people.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            var intent = Intent(this@MainActivity, PeopleActivity::class.java)
            startActivity(intent)
        }






    }


    override fun onRestart() {
        super.onRestart()
    }



    override fun onResume() {
        super.onResume()



    }

    override fun onPause() {
        super.onPause()




    }







//暂时无用
    //            if (vbvCommunity != null) {
//                vbvCommunity.performSwitch()
//            }


    //webview 中 视频全屏


//    private var mCustomView:View? = null
//    private var mOriginalSystemUiVisibility = 0
//    private var mOriginalOrientation = 0
//    private var mCustomViewCallback : WebChromeClient.CustomViewCallback? = null
//
//    override fun onShowCustomView(view: View?, callback: WebChromeClient.CustomViewCallback?) {
//        super.onShowCustomView(view, callback)
//        Utils.log("111","onShowCustomView")
//
//        if (mCustomView != null)
//        {
//            onHideCustomView()
//            return
//        }
//
//        // 1. Stash the current state
//        mCustomView = view
//        mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility()
//        mOriginalOrientation = getRequestedOrientation()
//
//        // 2. Stash the custom view callback
//        mCustomViewCallback = callback
//
//        // 3. Add the custom view to the view hierarchy
//        val decor = getWindow().getDecorView() as FrameLayout
//        decor.addView(mCustomView, FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT))
//
//
//        // 4. Change the state of the window
//        getWindow().getDecorView().setSystemUiVisibility(
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_IMMERSIVE)
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//
//    }
//
//    override fun onHideCustomView() {
//        super.onHideCustomView()
//        Utils.log("111","onHideCustomView")
//
//        // 1. Remove the custom view
//        val decor = getWindow().getDecorView() as FrameLayout
//        decor.removeView(mCustomView)
//        mCustomView = null
//
//        // 2. Restore the state to it's original form
//        getWindow().getDecorView()
//            .setSystemUiVisibility(mOriginalSystemUiVisibility)
//        setRequestedOrientation(mOriginalOrientation)
//
//        // 3. Call the custom view callback
//        mCustomViewCallback?.onCustomViewHidden()
//        mCustomViewCallback = null
//
//    }


}
