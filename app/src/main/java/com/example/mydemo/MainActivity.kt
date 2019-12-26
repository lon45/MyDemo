package com.example.mydemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.countdown.CountDownTimerActivity
import com.example.mydemo.deviceInfo.DeviceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_countdown.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //倒计时
            var intent = Intent(this@MainActivity, CountDownTimerActivity::class.java)
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

        tv_device.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //设备
            var intent = Intent(this@MainActivity, DeviceActivity::class.java)
            startActivity(intent)

        }



        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setOnVideoSizeChangedListener { mediaPlayer, _, _ ->
                val videoW = mediaPlayer.videoWidth
                val videoH = mediaPlayer.videoHeight

                val display = windowManager.defaultDisplay
                val width = display.width
                val height = display.height

                val scale = width.toFloat() / videoW.toFloat()
                val w = videoW * scale
                val h = videoH * scale
                //videoview.setY(-(h-height)/4);
                videoView.holder.setFixedSize(w.toInt(), h.toInt())
                videoView.setMeasure(w.toInt(), h.toInt())
                videoView.requestLayout()
            }
        }
        startVideoBg()

    }


    //播放登录前的视频
    private fun startVideoBg() {
        Log.i("111", "startVideoBg")
        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.login))
        //播放
        videoView.start()
        //循环播放
        videoView.setOnCompletionListener { videoView.start() }
        Log.i("111", "startVideoBg  end")
    }
}
