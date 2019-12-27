package com.example.mydemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.example.mydemo.downLoad.DownLoadActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.countdown.CountDownTimerActivity
import com.example.mydemo.deviceInfo.DeviceActivity
import com.example.mydemo.interfaces.OnCallback
import com.example.mydemo.views.rollingtextview.CharOrder
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

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

        bannerHandler = BannerHandler(this)
        rtvNum.addCharOrder(CharOrder.Number)
        rtvNum.animationDuration = 500L
        rtvNum.letterSpacingExtra = 15
        rtvNum.showCharBg = true

        initCommunity()
    }

    override fun onRestart() {
        super.onRestart()
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

    fun initCommunity() {
        val cbb = ArrayList<CommunityBannerBean>()
        cbb.add(
            CommunityBannerBean(
                "HR俱乐部",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E4%BA%BA%E5%8A%9B3.png",
                "1563"
            )
        )
        cbb.add(
            CommunityBannerBean(
                "人文与领导力俱乐部",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E4%BA%BA%E6%96%871.png",
                "1300"
            )
        )
        cbb.add(
            CommunityBannerBean(
                "创投俱乐部",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E5%88%9B%E6%8A%95.png",
                "1424"
            )
        )
        cbb.add(
            CommunityBannerBean(
                "大健康产业联盟",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E5%A4%A7%E5%81%A5%E5%BA%B73.png",
                "1655"
            )
        )
        cbb.add(
            CommunityBannerBean(
                "智造供应链分会",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E7%89%A9%E6%B5%811.jpg",
                "936"
            )
        )
        cbb.add(
            CommunityBannerBean(
                "董秘俱乐部",
                "https://static.chuanghehui.com/othermaterial/app/club-images/%E8%91%A3%E7%A7%981.png",
                "452"
            )
        )

        if (vbvCommunity.adapter == null) {
            vbvCommunity.adapter = CommunityBannerAdapter(this, cbb)
        } else {
            vbvCommunity.adapter.setData(cbb)
        }

        vbvCommunity.setOnCallbackAny(object : OnCallback {
            override fun onCallback(ob: Any) {
                rtvNum.setText(cbb[ob as Int % cbb.size].num)
            }
        })
        rtvNum.setText(cbb[0].num)

//        bannerHandler.removeMessages(0)
//        bannerHandler.sendEmptyMessageDelayed(0, 5000)
//        bannerMove = true
    }

    override fun onResume() {
        super.onResume()

//        if (bannerMove) {
            bannerHandler.sendEmptyMessageDelayed(0, 5000)
//        }
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacksAndMessages(null)
    }
    private lateinit var bannerHandler: BannerHandler
//    private var bannerMove = false

    inner class BannerHandler(activity: Activity) : Handler() {

        override fun handleMessage(msg: Message) {
            if (vbvCommunity != null) {
                vbvCommunity.performSwitch()
            }
            bannerHandler.sendEmptyMessageDelayed(0, 5000)

        }
    }

//暂时无用
    //            if (vbvCommunity != null) {
//                vbvCommunity.performSwitch()
//            }
}
