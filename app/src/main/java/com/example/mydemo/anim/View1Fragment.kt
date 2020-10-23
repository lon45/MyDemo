package com.example.mydemo.anim

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydemo.*
import com.example.mydemo.banner1.CommunityBannerAdapter
import com.example.mydemo.banner1.CommunityBannerBean
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.interfaces.OnCallback
import com.example.mydemo.views.rollingtextview.CharOrder
import kotlinx.android.synthetic.main.fragment_view1.*

/**
 *Date: 2020/9/10
 *author: hxc
 */
class View1Fragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view1,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bannerHandler = BannerHandler(activity!!)
        rtvNum.addCharOrder(CharOrder.Number)
        rtvNum.animationDuration = 500L
        rtvNum.letterSpacingExtra = 15
        rtvNum.showCharBg = true

        initCommunity()

    }


    override fun onResume() {
        super.onResume()
        bannerHandler.sendEmptyMessageDelayed(0, 5000)
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacksAndMessages(null)
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
            vbvCommunity.adapter =
                CommunityBannerAdapter(
                    activity!!,
                    cbb
                )
        } else {
            vbvCommunity.adapter.setData(cbb)
        }

        vbvCommunity.setOnCallbackAny(object : OnCallback {
            override fun onCallback(ob: Any) {
                rtvNum.setText(cbb[ob as Int % cbb.size].num)
            }
        })
        rtvNum.setText(cbb[0].num)

    }

    private lateinit var bannerHandler: BannerHandler

    inner class BannerHandler(activity: Activity) : Handler() {

        override fun handleMessage(msg: Message) {
            if (vbvCommunity != null) {
                vbvCommunity.performSwitch()
            }
            bannerHandler.sendEmptyMessageDelayed(0, 5000)

        }
    }
}