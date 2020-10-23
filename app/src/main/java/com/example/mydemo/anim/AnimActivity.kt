package com.example.mydemo.anim

import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_anim.*

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