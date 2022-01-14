package com.example.mydemo.activity

import android.graphics.Color
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.os.Bundle
import androidx.annotation.ColorInt
import com.app.chuanghehui.commom.utils.PermissionUtils
import com.example.mydemo.R
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.util.Utils
import com.nader.collectmiclib.CollectMic
import com.nader.collectmiclib.interfaces.OnMicListener
import kotlinx.android.synthetic.main.activity_mic.*

/**
 *Date: 2021/5/12
 *author: hxc
 */
class MicActivity : BaseActivity() {
    var visualizer: Visualizer? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_mic
    }

    override fun initView() {

        CollectMic.getInstance().addOnMicListener(object :
            OnMicListener {
            override fun mic(color: Int) {
                Utils.log("mic","$color")
                tv_get.setBackgroundColor(color)
//                tv_get.setBackgroundColor(
//                    Color.argb(
//                        255,
//                        Color.red(color),
//                        Color.green(color),
//                        Color.blue(color)
//                    )
//                )
            }
        })


    }

    override fun addListener() {
        tv_start.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            CollectMic.getInstance().startMic()
        }
        tv_stop.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            CollectMic.getInstance().stopMic()

        }
        tv_get.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CollectMic.getInstance().destroyMic()
    }

}