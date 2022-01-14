package com.example.mydemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *Date: 2019/7/31
 *author: hxc
 */
abstract class BaseActivity : AppCompatActivity(),ActivityInterface {

    val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        CacheActivityManager.addActivity(this)
        initView()
        addListener()
    }

    fun exitActivity(){
        CacheActivityManager.finishSingleActivity(this)
    }

}