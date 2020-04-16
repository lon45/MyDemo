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
import com.example.mydemo.views.MyCircleProgress


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val typeface = ResourcesCompat.getFont(this, R.font.xingshu)
        tv_progress.typeface = typeface
        tv_progress.text = "已抢了50%;"

        mycp.setValue(5)

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

        tv_liandong.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            //联动
//            var intent = Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)
//            startActivity(intent)
            var intent = Intent(this@MainActivity, TestActivity::class.java)
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
        //根据色值判断点击事件
        tv_people.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            var intent = Intent(this@MainActivity, PeopleActivity::class.java)
            startActivity(intent)
        }

        videoBackGround()

        bannerHandler = BannerHandler(this)
        rtvNum.addCharOrder(CharOrder.Number)
        rtvNum.animationDuration = 500L
        rtvNum.letterSpacingExtra = 15
        rtvNum.showCharBg = true

        initCommunity()

        viewpager()
    }


    override fun onRestart() {
        super.onRestart()
    }

    //视频背景
    private fun videoBackGround() {

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

    private var currentPagerPosition = 0//100
    private var needAutoMove = false
    // 无限循环
    private fun viewpager() {
        mViewpagerHandler = MyViewpagerHandler(this)
        val datas = arrayListOf(
            "https://static.chuanghehui.com/othermaterial/app/club-images/%E4%BA%BA%E5%8A%9B3.png",
            "https://static.chuanghehui.com/othermaterial/app/club-images/%E4%BA%BA%E6%96%871.png",
            "https://static.chuanghehui.com/othermaterial/app/club-images/%E5%88%9B%E6%8A%95.png"
        )
        indicatorContainerLL.removeAllViews()
        mViewpagerHandler.removeCallbacksAndMessages(null)

        if (datas.size == 1) {
            needAutoMove = false
            mViewpagerHandler.removeCallbacksAndMessages(null)
        } else {
            for (i in 0 until datas.size) {
                var layoutParams = LinearLayout.LayoutParams(0, Utils.dpToPx(this, 2f))
                layoutParams.weight = 1f
                layoutParams.height = Utils.dpToPx(this, 2f)
                layoutParams.leftMargin = Utils.dpToPx(this, 3f)
                layoutParams.rightMargin = Utils.dpToPx(this, 3f)
                val indicator = LayoutInflater.from(this).inflate(R.layout.view_indicator, null)
                indicator.layoutParams = layoutParams
                indicatorContainerLL.addView(indicator, i)
            }

            needAutoMove = true
            if (!mViewpagerHandler.hasMessages(0)) {
                mViewpagerHandler.sendEmptyMessageDelayed(0, 2000)
            }

            currentPagerPosition = 0//banner.size //* 8
            indicatorContainerLL.forEach {
                it.indicatorIV.isEnabled = false
            }
            if (indicatorContainerLL.getChildAt(currentPagerPosition) != null) {
                indicatorContainerLL.getChildAt(currentPagerPosition)?.indicatorIV!!.isEnabled = true
            }

            bannerVP.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {

                        needAutoMove = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        needAutoMove = false
                    }
                    MotionEvent.ACTION_UP -> {
                        needAutoMove = true
                        if (datas.size == 1) {
                            needAutoMove = false
                        }
                        mViewpagerHandler.removeCallbacksAndMessages(null)
                        mViewpagerHandler.sendEmptyMessageDelayed(0, 5000)
                    }
                }
                return@setOnTouchListener false
            }

            bannerVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {

                    indicatorContainerLL.forEach {
                        it.indicatorIV.isEnabled = false
                    }
                    if (indicatorContainerLL.getChildAt(position % datas.size) != null) {
                        indicatorContainerLL.getChildAt(position % datas.size)?.indicatorIV!!.isEnabled = true
                    }
                    currentPagerPosition = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

        }

        bannerVP.adapter = MyViewpager(this, datas) {

        }
        bannerVP.offscreenPageLimit = 3
        bannerVP.currentItem = datas.size * 100

    }


    //播放登录前的视频
    private fun startVideoBg() {
//        Log.i("111", "startVideoBg")
        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.login))
        //播放
        videoView.start()
        //循环播放
        videoView.setOnCompletionListener { videoView.start() }
//        Log.i("111", "startVideoBg  end")
    }

    private fun initCommunity() {
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

    var anim: ValueAnimator? = null
    override fun onResume() {
        super.onResume()

        if (needAutoMove) {
            mViewpagerHandler.sendEmptyMessageDelayed(0, 5000)
        }

//        if (bannerMove) {
        bannerHandler.sendEmptyMessageDelayed(0, 5000)
//        }

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
        bannerHandler.removeCallbacksAndMessages(null)
        mViewpagerHandler.removeCallbacksAndMessages(null)

        if (anim != null && anim!!.isRunning) {
            anim!!.cancel()
            anim = null
        }
    }

    inner class MyViewpagerHandler(activity: Activity) : Handler() {
        private val mActivity: WeakReference<Activity> = WeakReference<Activity>(activity)

        override fun handleMessage(msg: Message) {
            val activity = mActivity.get()
            if (activity != null && needAutoMove) {
                currentPagerPosition++
                bannerVP?.currentItem = currentPagerPosition
                mViewpagerHandler.sendEmptyMessageDelayed(0, 5000)
            }
        }
    }

    private lateinit var mViewpagerHandler: MyViewpagerHandler


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
