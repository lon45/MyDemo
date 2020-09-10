package com.example.mydemo.anim

import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Rect
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
import com.example.mydemo.BaseActivity
import com.example.mydemo.MyViewpager
import com.example.mydemo.R
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.activity_anim.*
import kotlinx.android.synthetic.main.view_indicator.view.*
import java.lang.ref.WeakReference

/**
 *Date: 2020/9/10
 *author: hxc
 * 动画类
 */
class AnimActivity : BaseActivity() {

    var anim: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

    }

    override fun onResume() {
        super.onResume()

        if (anim == null) {
            anim = ValueAnimator.ofFloat(0f, 1f)

            anim!!.addUpdateListener {
                view_anim.visibility = View.VISIBLE
                Log.i("anim", "${it.animatedValue} | ${view_anim.width}")
                it.animatedValue
                //设置View的显示区域，坐标是自身
                var tmp =
                    Rect(0, 0, (it.animatedValue.toString().toFloat() * view_anim.width).toInt(), view_anim.height)
                view_anim.clipBounds = tmp

            }
            anim!!.duration = 3000
            anim!!.start()
        }

    }



    override fun onPause() {
        super.onPause()

        if (anim != null && anim!!.isRunning) {
            anim!!.cancel()
            anim = null
        }
    }




}