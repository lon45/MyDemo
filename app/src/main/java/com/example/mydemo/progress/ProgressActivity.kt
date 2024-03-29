package com.example.mydemo.progress

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_progress.*

/**
 *Date: 2020/9/10
 *author: hxc
 * 进度条类
 */
class ProgressActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_progress
    }

    override fun initView() {

        mycp.setValue(5)

        val typeface = ResourcesCompat.getFont(this, R.font.xingshu)
        tv_progress.typeface = typeface
        tv_progress.text = "已抢了50%;"
    }

    override fun addListener() {

    }

}