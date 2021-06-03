package com.example.mydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.item_left_scroll.view.*

/**
 *Date: 2019/11/28
 *author: hxc
 */
class DragSortAdapter(var mContext: Context, var list: ArrayList<String>, private var onItemClick : (item:String) -> Unit) :
    RecyclerView.Adapter<DragSortAdapter.ViewHolder>() {
    val inflater = LayoutInflater.from(mContext)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = inflater.inflate(R.layout.item_left_scroll, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = list[position]

        holder.itemView.tv_content.text = item

        holder.itemView.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            onItemClick(item)

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val Tag = DragSortAdapter::class.java.simpleName

    }

}