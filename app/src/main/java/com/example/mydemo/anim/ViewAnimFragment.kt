package com.example.mydemo.anim

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.R
import kotlinx.android.synthetic.main.fragment_viewanim.*

/**
 *Date: 2020/9/10
 *author: hxc
 */
class ViewAnimFragment: BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_viewanim
    }


    override fun initView() {
        videoBackGround()
    }

    override fun addListener() {

    }

    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
    }

    //视频背景
    private fun videoBackGround() {

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setOnVideoSizeChangedListener { mediaPlayer, _, _ ->
                val videoW = mediaPlayer.videoWidth
                val videoH = mediaPlayer.videoHeight

                val display = activity!!.windowManager.defaultDisplay
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
        videoView.setVideoURI(Uri.parse("android.resource://" + activity!!.packageName + "/" + R.raw.login))
        //播放
        videoView.start()
        //循环播放
        videoView.setOnCompletionListener { videoView.start() }
    }
}