package com.nader.collectmiclib

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import com.nader.collectmiclib.BuildConfig.VERSION_CODE
import com.nader.collectmiclib.bean.SoundBean
import com.nader.collectmiclib.interfaces.OnMicListener
import com.nader.collectmiclib.utils.ColorUtils
import kotlin.math.abs

/**
 *Date: 2021/5/13
 *author: hxc
 * 采集麦克风控制类
 */
class CollectMic {

    companion object {
        /**
         * 声音信息
         */
        var SOUND_MESSAGE = 0x110

        private lateinit var instance: CollectMic
        private fun isInitialized() = ::instance.isInitialized

        fun getInstance(): CollectMic {
            if (!isInitialized()) {
                instance = CollectMic()
            }
            return instance
        }
    }

    private var soundAnalysis: SoundAnalysis? = null

    constructor() {
        soundAnalysis = SoundAnalysis(myHander)
    }

    private var mOnMicListener: OnMicListener? = null
    fun addOnMicListener(onMicListener: OnMicListener) {
        this.mOnMicListener = onMicListener
    }

    //开始采集麦克风
    fun startMic() {
        if (soundAnalysis != null) {
            soundAnalysis!!.startRecord()

        }
    }

    fun stopMic() {
        if (soundAnalysis != null) {
            soundAnalysis!!.stopRecord()

        }
    }

    fun destroyMic() {
        if (soundAnalysis != null) {
            soundAnalysis!!.destroy()

        }
    }

    /**
     * 最大的声音
     */
    private val MAX_SOUND = 1000.0

    @SuppressLint("HandlerLeak")
    private var myHander = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == SOUND_MESSAGE) {
                val soundBean = msg.obj as SoundBean
//
                val frequency = soundBean.mFrequency.toInt()

                var alpha = (255 * soundBean.mVolume / MAX_SOUND).toInt()
                if (alpha > 255) {
                    alpha = 255
                }
                val color: Int = ColorUtils.COLOR_LIST_140[frequency % 140]
//                Log.i("Color_s2","${alpha}|${Color.red(color)}|${Color.green(color)}|${Color.blue(color)}")

                if (mOnMicListener != null) {
                    mOnMicListener!!.mic(Color.argb(
                        200,
                        Color.red(color),
                        Color.green(color),
                        Color.blue(color)
                    ))
                }
            }
        }
    }
}