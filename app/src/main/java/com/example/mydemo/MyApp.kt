package com.example.mydemo

import android.os.Environment
import com.example.mydemo.util.DIR_CHE
import androidx.multidex.MultiDexApplication

/**
 *Date: 2019/12/18
 *author: hxc
 */
class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initPath()
    }


    private fun initPath() {

        //初始化 缓存地址
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            /**
             *  /storage/emulated/0/Android/data/com.app.chuanghehui/cache
             * */
            DIR_CHE = externalCacheDir!!.path
        } else {
            /**
             *  /data/user/0/com.app.chuanghehui/cache
             * */
            DIR_CHE = cacheDir!!.path
        }

    }
}

