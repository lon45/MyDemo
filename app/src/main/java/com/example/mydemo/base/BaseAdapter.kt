package com.example.mydemo.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *Date: 2022/1/14
 *author: hxc
 */
abstract class BaseAdapter(mContext:Context):RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    val Tag = this.javaClass.name

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(getLayoutID(), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return getDataSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        updateData(holder,position)
    }

    abstract fun getLayoutID():Int
    abstract fun getDataSize():Int
    abstract fun updateData(holder: ViewHolder, position: Int)

}