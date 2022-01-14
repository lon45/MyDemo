package com.example.mydemo.main

import android.content.Context
import com.example.mydemo.R
import com.example.mydemo.base.BaseAdapter

/**
 *Date: 2022/1/14
 *author: hxc
 */
class MainAdapter(mContext:Context,val data:ArrayList<String>):BaseAdapter(mContext) {
    override fun getLayoutID(): Int {
        return R.layout.item_main
    }

    override fun getDataSize(): Int {
        return data.size
    }

    override fun updateData(holder: ViewHolder, position: Int) {

        

    }


}