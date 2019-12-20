package com.example.mydemo.DownLoad

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.DownLoad.bean.DownLoadInfo
import com.example.mydemo.R
import com.example.mydemo.Util.Utils
import kotlinx.android.synthetic.main.item_down_load.view.*

/**
 *Date: 2019/11/28
 *author: hxc
 */
class DownLoadAdapter(var mContext: Context, var list: ArrayList<DownLoadInfo> , private var onItemClick : (item:DownLoadInfo) -> Unit) :
    RecyclerView.Adapter<DownLoadAdapter.ViewHolder>() {
    val inflater = LayoutInflater.from(mContext)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = inflater.inflate(R.layout.item_down_load, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = list[position]
        holder.itemView.tv_title.text = item.lesson_name
//        holder.itemView.tv_pro.text = item.finished.toString()
        when (item.downLoad_state) {
            DownLoaderManger.STATE_START// 开始下载
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.color_main_theme))
                holder.itemView.tv_pro.text = "开始下载"
            }
            DownLoaderManger.STATE_WAITING// 准备下载
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.color_main_theme))
                holder.itemView.tv_pro.text = "准备下载"
            }
            DownLoaderManger.STATE_DOWNLOADING// 下载中
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.color_main_theme))
                holder.itemView.tv_pro.text = "下载中"
                if (item.lesson_size > 0) {
                    holder.itemView.tv_pro.append("${item.finished *100 / item.lesson_size} %")
                }
            }
            DownLoaderManger.STATE_PAUSED// 暂停
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.text_light))
                holder.itemView.tv_pro.text = "已暂停，点击继续下载"
            }
            DownLoaderManger.STATE_DOWNLOADED// 下载完毕
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.color_main_theme))
                holder.itemView.tv_pro.text = "下载完毕"
            }
            DownLoaderManger.STATE_ERROR// 下载失败
            -> {
//                    tvDown.setTextColor(getContext().getResources().getColor(R.color.color_main_theme))
                holder.itemView.tv_pro.text = "下载失败"
            }
            DownLoaderManger.STATE_DELETE// 删除成功
            -> {
            }
            }

        holder.itemView.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            onItemClick(item)


        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val Tag = DownLoadAdapter::class.java.simpleName

    }
}