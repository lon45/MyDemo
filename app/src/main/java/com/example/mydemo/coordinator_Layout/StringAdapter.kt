package com.example.mydemo.coordinator_Layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import kotlinx.android.synthetic.main.item_string.view.*

/**
 *Date: 2020/4/8
 *author: hxc
 */
class StringAdapter(val mContext:Context,val data : ArrayList<String>) :RecyclerView.Adapter<StringAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_string,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = data[position]

        holder.itemView.tv_string.text = item

    }


    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

}