package com.example.mydemo.anim

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.viewpager.widget.ViewPager
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.banner1.MyViewpager
import com.example.mydemo.R
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlinx.android.synthetic.main.view_indicator.view.*
import java.lang.ref.WeakReference

/**
 *Date: 2020/9/10
 *author: hxc
 */
class ViewpagerFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager()
    }

    override fun onResume() {
        super.onResume()
        if (needAutoMove) {
            mViewpagerHandler.sendEmptyMessageDelayed(0, 5000)
        }

    }

    override fun onPause() {
        super.onPause()

        mViewpagerHandler.removeCallbacksAndMessages(null)
    }

    private var currentPagerPosition = 0//100
    private var needAutoMove = false
    // 无限循环
    private fun viewpager() {
        mViewpagerHandler = MyViewpagerHandler(activity!!)
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
                var layoutParams = LinearLayout.LayoutParams(0, Utils.dpToPx(activity!!, 2f))
                layoutParams.weight = 1f
                layoutParams.height = Utils.dpToPx(activity!!, 2f)
                layoutParams.leftMargin = Utils.dpToPx(activity!!, 3f)
                layoutParams.rightMargin = Utils.dpToPx(activity!!, 3f)
                val indicator = LayoutInflater.from(activity!!).inflate(R.layout.view_indicator, null)
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

        bannerVP.adapter =
            MyViewpager(activity!!, datas) {

            }
        bannerVP.offscreenPageLimit = 3
        bannerVP.currentItem = datas.size * 100

    }

    /***viewpager  start*******/
    private lateinit var mViewpagerHandler: MyViewpagerHandler

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

    /***viewpager  end*******/
}