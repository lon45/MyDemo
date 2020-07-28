package com.example.mydemo.countdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import kotlinx.android.synthetic.main.item_count_down.view.*

/**
 *Date: 2019/11/20
 *author: hxc
 */
class CountDownAdapter(val mContext: Context, val timeList: ArrayList<Int>) :
    RecyclerView.Adapter<CountDownAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_count_down, null)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (timeList.isNotEmpty()) {

            var item = timeList[position]

            holder.itemView.myCountTimer.setTime(item)
            holder.itemView.myCountTimer2.setTime(item)

        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private val TAG = CountDownAdapter::class.java.simpleName
    }
}