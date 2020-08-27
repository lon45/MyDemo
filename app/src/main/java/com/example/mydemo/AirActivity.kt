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
import com.example.mydemo.coordinator_Layout.CoordinatorLayoutActivity
import com.example.mydemo.leftscroll.LeftScrollActivity
import com.example.mydemo.views.MyCircleProgress
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



