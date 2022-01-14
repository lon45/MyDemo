package com.example.mydemo.main

import android.content.Context
import com.example.mydemo.R
import com.example.mydemo.base.BaseAdapter
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.item_main.view.*

/**
 *Date: 2022/1/14
 *author: hxc
 */
class MainAdapter(mContext:Context,val data:ArrayList<String>,val onItemClick:(item:String)-> Unit):BaseAdapter(mContext) {
    override fun getLayoutID(): Int {
        return R.layout.item_main
    }

    override fun getDataSize(): Int {
        return data.size
    }

    override fun updateData(holder: ViewHolder, position: Int) {

        val item = data[position]
        holder.itemView.tv_content.text = item

        holder.itemView.setOnClickListener {
            if(!Utils.isFastDoubleClick()){
                return@setOnClickListener
            }
            onItemClick(item)

        }

    }


}