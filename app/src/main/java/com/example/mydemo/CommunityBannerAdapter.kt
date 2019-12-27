package com.example.mydemo

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mydemo.views.VerticalBannerView.BaseBannerAdapter
import com.example.mydemo.views.VerticalBannerView.VerticalBannerView
import kotlinx.android.synthetic.main.fragment_community_banner_item.view.*

/**
 * 首页活动预告
 * @author Ys
 * @date 2018-07-16.
 */

class CommunityBannerAdapter(private val mContext: Activity, private val datas: List<CommunityBannerBean>) :
    BaseBannerAdapter<CommunityBannerBean>(datas) {

    private val mDatas: List<CommunityBannerBean>? = null

    override fun getView(parent: VerticalBannerView): View {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_community_banner_item, null)
    }

    override fun setItem(view: View, data: CommunityBannerBean) {

        view.tvTitle.text = data.title
        if (mContext != null && !mContext.isDestroyed) {
            Glide.with(mContext)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(view.ivTitle)
        }
    }
}

